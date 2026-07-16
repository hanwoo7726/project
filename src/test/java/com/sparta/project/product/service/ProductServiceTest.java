package com.sparta.project.product.service;

import com.sparta.project.product.entity.Product;
import com.sparta.project.product.repository.ProductRepository;
import com.sparta.project.product.dto.request.ProductCreateRequest;
import com.sparta.project.product.dto.response.ProductResponse;
import com.sparta.project.store.entity.Store;
import com.sparta.project.store.repository.StoreRepository;
import com.sparta.project.user.entity.User;
import com.sparta.project.user.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductAiService productAiService;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private ProductService productService;

    private static final Long STORE_ID = 1L;

    @Test
    @DisplayName("AI 미사용 시 상품이 정상 등록")
    void createProduct_success_withoutAi() {
        // given - 준비
        User owner = createUser(1L, "owner1111", UserRoleEnum.OWNER);
        ProductCreateRequest request = new ProductCreateRequest();
        // 리플렉션으로 필드 세팅
        setField(request, "storeId", STORE_ID);
        setField(request, "name", "만두만두");
        setField(request, "price", 8000);
        setField(request, "aiGenerate", false);

        Product savedProduct = Product.create(STORE_ID, "만두만두", 8000, null, 1);

        given(storeRepository.findByIdAndDeletedAtIsNull(STORE_ID))
                .willReturn(Optional.of(createStore("owner1111")));
        given(productRepository.findMaxDisplayOrder(STORE_ID)).willReturn(0);
        given(productRepository.save(any(Product.class))).willReturn(savedProduct);

        // when - 실행
        ProductResponse response = productService.createProduct(request, owner);

        // then - 검증
        assertThat(response.getName()).isEqualTo("만두만두");
        assertThat(response.getPrice()).isEqualTo(8000);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("남의 가게에 상품 등록 시 예외가 발생")
    void createProduct_fail_notOwner() {
        // given - 다른 사람(owner2222)이 owner1111의 가게에 등록 시도
        User otherOwner = createUser(2L, "owner2222", UserRoleEnum.OWNER);
        ProductCreateRequest request = new ProductCreateRequest();
        setField(request, "storeId", STORE_ID);
        setField(request, "name", "남의가게 상품");
        setField(request, "price", 8000);
        setField(request, "aiGenerate", false);

        given(storeRepository.findByIdAndDeletedAtIsNull(STORE_ID))
                .willReturn(Optional.of(createStore("owner1111")));

        // when & then
        assertThatThrownBy(() -> productService.createProduct(request, otherOwner))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("본인 가게에만 상품을 등록할 수 있습니다");
    }

    @Test
    @DisplayName("ADMIN은 남의 가게에도 상품 등록 가능")
    void createProduct_success_admin() {
        // given
        User admin = createUser(3L, "admin1111", UserRoleEnum.ADMIN);
        ProductCreateRequest request = new ProductCreateRequest();
        setField(request, "storeId", STORE_ID);
        setField(request, "name", "관리자 등록 상품");
        setField(request, "price", 8000);
        setField(request, "aiGenerate", false);

        Product savedProduct = Product.create(STORE_ID, "관리자 등록 상품", 8000, null, 1);

        given(productRepository.findMaxDisplayOrder(STORE_ID)).willReturn(0);
        given(productRepository.save(any(Product.class))).willReturn(savedProduct);

        // when - ADMIN은 소유권 검증을 우회하므로 storeRepository 조회 셍랙
        ProductResponse response = productService.createProduct(request, admin);

        // then
        assertThat(response.getName()).isEqualTo("관리자 등록 상품");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 예외가 발생")
    void getProduct_notFound() {
        // given
        UUID productId = UUID.randomUUID();
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("삭제된 상품 조회 시 예외가 발생")
    void getProduct_deleted() {
        // given
        UUID productId = UUID.randomUUID();
        Product deletedProduct = Product.create(STORE_ID, "삭제된 상품", 8000, null, 1);
        deletedProduct.delete(1L);

        given(productRepository.findById(productId)).willReturn(Optional.of(deletedProduct));

        // when & then
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("남의 상품 삭제 시 예외가 발생")
    void deleteProduct_fail_notOwner() {
        // given
        UUID productId = UUID.randomUUID();
        User otherOwner = createUser(2L, "owner2222", UserRoleEnum.OWNER);
        Product product = Product.create(STORE_ID, "만두만두", 8000, null, 1);

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(storeRepository.findByIdAndDeletedAtIsNull(STORE_ID))
                .willReturn(Optional.of(createStore("owner1111")));

        // when & then
        assertThatThrownBy(() -> productService.deleteProduct(productId, otherOwner))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("본인 가게의 상품만 관리할 수 있습니다");
    }

    @Test
    @DisplayName("본인 상품 삭제 시 deletedAt과 deletedBy가 기록")
    void deleteProduct_success() {
        // given
        UUID productId = UUID.randomUUID();
        User owner = createUser(1L, "owner1111", UserRoleEnum.OWNER);
        Product product = Product.create(STORE_ID, "만두만두", 8000, null, 1);

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(storeRepository.findByIdAndDeletedAtIsNull(STORE_ID))
                .willReturn(Optional.of(createStore("owner1111")));

        // when
        productService.deleteProduct(productId, owner);

        // then - Soft Delete가 기록되었는지
        assertThat(product.getDeletedAt()).isNotNull();
        assertThat(product.getDeletedBy()).isEqualTo(1L);
    }

    // 테스트용 User 생성 (생성자가 없어서)
    private User createUser(Long id, String username, UserRoleEnum role) {
        try {
            var constructor = User.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            User user = constructor.newInstance();
            setField(user, "id", id);
            setField(user, "username", username);
            setField(user, "userRoleEnum", role);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 테스트용 Store 생성 (ownerUsername만 필요)
    private Store createStore(String ownerUsername) {
        try {
            var constructor = Store.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Store store = constructor.newInstance();
            setField(store, "id", STORE_ID);
            setField(store, "ownerUsername", ownerUsername);
            return store;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 테스트용 리플렉션 헬퍼 (DTO에 Setter가 없어서..)
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}