package com.order_service.dto.response;

import com.core.entity.type.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.order_service.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {

    private ProductType productType;

    private Long productId;

    private Integer quantity;

    private Long paymentId;

    private Boolean isOrdered;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public static OrderResponse from(Order order) {
        if (order == null) return null;

        return OrderResponse.builder()
                .productType(order.getProductType())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .paymentId(order.getPaymentId())
                .isOrdered(order.getIsOrdered())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}
