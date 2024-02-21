package com.reserved_product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.core.entity.type.ProductType;
import com.reserved_product_service.entity.ReservedProduct;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductListResponse {

    private String title;

    private Integer price;

    private ProductType productType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static ProductListResponse from(ReservedProduct reservedProduct) {
        if (reservedProduct == null) return null;

        return ProductListResponse.builder()
                .title(reservedProduct.getTitle())
                .price(reservedProduct.getPrice())
                .productType(ProductType.RESERVED)
                .createdAt(reservedProduct.getCreatedAt())
                .build();
    }

}
