package com.sparta.project.product.dto.response;

import com.sparta.project.product.entity.Product;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductResponse {

    private final UUID productId;
    private final UUID storeID;
    private final String name;
    private final Integer price;
    private final Boolean isHidden;
    private final Integer displayOrder;

    private ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.storeID = product.getStoreId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.isHidden = product.getIsHidden();
        this.displayOrder = product.getDisplayOrder();
    }

    public static ProductResponse from(Product product){
        return new ProductResponse(product);
    }
}
