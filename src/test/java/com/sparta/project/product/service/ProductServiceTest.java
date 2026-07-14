package com.sparta.project.product.application.service;

import com.sparta.project.product.domain.entity.Product;
import com.sparta.project.product.domain.repository.ProductRepository;
import com.sparta.project.product.presentation.dto.request.ProductCreateRequest;
import com.sparta.project.product.presentation.dto.response.ProductResponse;
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

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("AI 미사용 시 상품이 정상 등록")
    void createProduct_success_withoutAi() {
        // given - 준비
        UUID storeId = UUID.randomUUID();
        ProductCreateRequest request = new ProductCreateRequest();
        // 리플렉션으로 필드 세팅
        setField(request, "storeId", storeId);
        setField(request, "name", "만두만두");
        setField(request, "price", 8000);
        setField(request, "aiGenerate", false);

        Product savedProduct = Product.create(storeId, "만두만두", 8000, null, 1);

        given(productRepository.findMaxDisplayOrder(storeId)).willReturn(0);
        given(productRepository.save(any(Product.class))).willReturn(savedProduct);

        // when - 실행
        ProductResponse response = productService.createProduct(request);

        // then - 검증
        assertThat(response.getName()).isEqualTo("만두만두");
        assertThat(response.getPrice()).isEqualTo(8000);
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