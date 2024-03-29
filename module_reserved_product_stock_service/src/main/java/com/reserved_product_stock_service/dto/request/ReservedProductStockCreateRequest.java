package com.reserved_product_stock_service.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservedProductStockCreateRequest {

    private Long reservedProductId;
    private Integer stock;

}
