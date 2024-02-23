package com.payment_service.dto.request;

import com.core.entity.type.ProductType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderCreateRequest {

    private Long userId;
    private Long productId;
    private ProductType productType;
    private Integer quantity;

}
