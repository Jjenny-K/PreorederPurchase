package com.order_service.dto.response;

import com.core.entity.type.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.order_service.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderListResponse {

    private ProductType productType;

    private Long productId;

    private Boolean isOrdered;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static OrderListResponse from(Order order) {
        if (order == null) return null;

        return OrderListResponse.builder()
                .productType(order.getProductType())
                .productId(order.getProductId())
                .isOrdered(order.getIsOrdered())
                .createdAt(order.getCreatedAt())
                .build();
    }

}
