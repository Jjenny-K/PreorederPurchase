package com.product_service.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductStockCreateRequest {

    private Long productId;
    private Integer stock;

}
