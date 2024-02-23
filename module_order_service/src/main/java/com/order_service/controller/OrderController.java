package com.order_service.controller;

import com.order_service.dto.response.OrderListResponse;
import com.order_service.dto.response.OrderResponse;
import com.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 목록 조회
    @GetMapping()
    public ResponseEntity<?> getOrderList(HttpServletRequest httpServletRequest) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        List<OrderListResponse> orderList =
                orderService.getOrderList(authorizedUserId).stream()
                        .map(OrderListResponse::from).toList();

        return ResponseEntity.ok().body(orderList);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(HttpServletRequest httpServletRequest,
                                      @PathVariable("orderId") String orderId) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        return ResponseEntity.ok().body(
                OrderResponse.from(orderService.getOrder(authorizedUserId, Long.valueOf(orderId))));
    }

}
