package com.payment_service.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderUpdateRequest {

    private Long paymentId;
    private Boolean isOrdered;
}
