package com.order_service.controller;

import com.order_service.dto.request.OrderCreateRequest;
import com.order_service.service.InternalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/orders")
public class InternalOrderController {

    private final InternalOrderService internalOrderService;

    // 결제 진입 주문서 발행
    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        internalOrderService.createOrder(orderCreateRequest);

        return ResponseEntity.ok().build();
    }

}
