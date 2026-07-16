package com.sparta.project.product.service;

import com.sparta.project.product.entity.Product;
import com.sparta.project.product.repository.ProductRepository;
import com.sparta.project.product.dto.request.ProductCreateRequest;
import com.sparta.project.product.dto.request.ProductUpdateRequest;
import com.sparta.project.product.dto.response.ProductResponse;
import com.sparta.project.store.entity.Store;
import com.sparta.project.store.repository.StoreRepository;
import com.sparta.project.user.entity.User;
import com.sparta.project.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    // 페이지 크기
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final List<Integer> ALLOWED_PAGE_SIZES = List.of(10, 20, 30);
    private final ProductAiService productAiService;

    private final StoreRepository storeRepository;

    // 상품 등록
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request, User user) {
        // 0. 소유권 검증
        validateStoreOwnership(request.getStoreId(), user);
        // 1. displayOrder 계산
        Integer displayOrder = request.getDisplayOrder();
        if(displayOrder == null){
            displayOrder = productRepository.findMaxDisplayOrder(request.getStoreId()) + 1;
        }
        else{
            productRepository.shiftDisplayOrder(request.getStoreId(), displayOrder);
        }

        // 2. 상품 먼저 생성
        String description = request.getDescription();

        Product product = Product.create(
                request.getStoreId(),
                request.getName(),
                request.getPrice(),
                description,
                displayOrder
        );
        Product savedProduct = productRepository.save(product);

        // 3. AI 생성 요청이면 설명 생성해서 갱신
        if(Boolean.TRUE.equals(request.getAiGenerate())){
            String aiDescription = productAiService.generateDescription(
                    savedProduct.getProductId(),
                    request.getAiPrompt(),
                    user.getUsername()
            );
            if(aiDescription != null){
                savedProduct.updateDescription(aiDescription);
            }
        }

        return ProductResponse.from(savedProduct);
    }

    // 상품 단건 조회
    @Transactional(readOnly = true)
    public ProductResponse getProduct(UUID productId) {
        Product product = findActivaProduct(productId);
        return ProductResponse.from(product);
    }

    // 상품 목록 조회
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(Long storeId, String keyword, Pageable pageable) {
        Pageable validatedPageable = validatePageSize(pageable);

        return productRepository.searchProducts(storeId, keyword, validatedPageable, false)
                .map(ProductResponse::from);
    }

    // 상품 수정
    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductUpdateRequest request, User user) {
        Product product = findActivaProduct(productId);
        validateOwnership(product, user);

        product.update(
               request.getName(),
               request.getPrice(),
               request.getDescription(),
               request.getDisplayOrder()
        );

        return ProductResponse.from(product);
    }

    // 상품 숨김/노출 전환
    @Transactional
    public ProductResponse updateVisibility(UUID productId, Boolean isHidden, User user){
        Product product = findActivaProduct(productId);
        validateOwnership(product, user);

        if (isHidden){
            product.hide();
        }
        else{
            product.show();
        }

        return ProductResponse.from(product);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(UUID productId, User user){
        Product product = findActivaProduct(productId);
        validateOwnership(product, user);

        product.delete(user.getId());
    }

    // 삭제되지 않은 상품 조회
    private Product findActivaProduct(UUID productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if(product.getDeletedAt() != null){
            throw new IllegalArgumentException("상품을 찾을 수 없습니다. ");
        }
        return product;
    }

    // 상품 소유권 검증
    private void validateOwnership(Product product, User user){
        if(user.getUserRoleEnum() == UserRoleEnum.ADMIN){
            return;
        }
        Store store = storeRepository.findByIdAndDeletedAtIsNull(product.getStoreId())
                .orElseThrow(()-> new IllegalArgumentException("가게를 찾을 수 없습니다. "));
        if(!store.getOwnerUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("본인 가게의 상품만 관리할 수 있습니다. ");
        }
    }

    // 가게 소유권 검증 (상품등록용)
    private void validateStoreOwnership(Long storeId, User user){
        if(user.getUserRoleEnum() == UserRoleEnum.ADMIN){
            return;
        }
        Store store = storeRepository.findByIdAndDeletedAtIsNull(storeId)
                .orElseThrow(()-> new IllegalArgumentException("가게를 찾을 수 없습니다. "));

        if (!store.getOwnerUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("본인 가게에만 상품을 등록할 수 있습니다. ");
        }
    }

    // 관리자 상품 목록 조회 (숨김/삭제 상품 포함)
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsForAdmin(Long storeId, String keyword, Pageable pageable) {
        Pageable validatedPageable = validatePageSize(pageable);

        return productRepository.searchProducts(storeId, keyword, validatedPageable, true)
                .map(ProductResponse::from);
    }


    // 페이지 크기 검증  10/20/30
    private Pageable validatePageSize(Pageable pageable){
        int size = pageable.getPageSize();

        if(ALLOWED_PAGE_SIZES.contains(size)){
            return PageRequest.of(pageable.getPageNumber(), DEFAULT_PAGE_SIZE, pageable.getSort());
        }
        return pageable;
    }
}
