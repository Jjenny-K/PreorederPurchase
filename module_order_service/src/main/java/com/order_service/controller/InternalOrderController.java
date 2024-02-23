package com.order_service.controller;

import com.core.dto.response.OrderCheckResponse;
import com.order_service.dto.request.OrderCreateRequest;
import com.order_service.dto.request.OrderUpdateRequest;
import com.order_service.entity.Order;
import com.order_service.service.InternalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 주문서 확인
    @GetMapping("/{orderId}")
    public ResponseEntity<?> checkOrder(@PathVariable("orderId") String orderId,
                                        @RequestParam(name = "userId") String userId) {
        Order order =
                internalOrderService.chekOrder(Long.valueOf(orderId), Long.valueOf(userId));

        OrderCheckResponse orderCheckResponse =
                new OrderCheckResponse(order.getProductType(), order.getProductId(), order.getQuantity());

        return ResponseEntity.ok().body(orderCheckResponse);
    }

    // 주문서 업데이트
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable("orderId") String orderId,
                                         @RequestBody OrderUpdateRequest orderUpdateRequest) {
        internalOrderService.updateOrder(Long.valueOf(orderId), orderUpdateRequest);

        return ResponseEntity.ok().build();
    }

}
