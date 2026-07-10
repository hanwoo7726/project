package com.sparta.project.product.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.project.product.domain.entity.Product;
import com.sparta.project.product.domain.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> searchProducts(UUID storeId, String keyword, Pageable pageable){
        QProduct product = QProduct.product;

        // 조건 조합 (null이면 무시)
        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(storeIdEq(storeId));
        conditions.and(nameContains(keyword));

        // 실제 데이터 조회
        List<Product> content = queryFactory
                .selectFrom(product)
                .where(conditions)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.productId.desc()) // createdAt으로 변경해야함 추후에
                .fetch();

        // 전체 개수 조회
        Long total = queryFactory
                .select(product.count())
                .from(product)
                .where(conditions)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    // storeId가 있으면 조건 추가, 없으면 null
    private BooleanExpression storeIdEq(UUID storeId) {
        return storeId != null ? QProduct.product.storeId.eq(storeId) : null;
    }
    // keyword가 있으면 상품명 부분 검색, 없으면 null
    private BooleanExpression nameContains(String keyword) {
        return StringUtils.hasText(keyword) ? QProduct.product.name.containsIgnoreCase(keyword) : null;
    }
}
