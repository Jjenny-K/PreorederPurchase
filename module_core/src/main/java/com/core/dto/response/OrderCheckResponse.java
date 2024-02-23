package com.core.dto.response;

import com.core.entity.type.ProductType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderCheckResponse {

    private ProductType productType;
    private Long productId;
    private Integer quantity;

}
