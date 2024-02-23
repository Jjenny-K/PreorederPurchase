package com.order_service.service;

import com.order_service.dto.request.OrderCreateRequest;
import com.order_service.dto.request.OrderUpdateRequest;
import com.order_service.entity.Order;
import com.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalOrderService {

    private static final Logger logger = LoggerFactory.getLogger(InternalOrderService.class);

    private final OrderRepository orderRepository;

    // 결제 진입 주문서 발행
    @Transactional
    public void createOrder(OrderCreateRequest orderCreateRequest) {
        Order order = Order.builder()
                .userId(orderCreateRequest.getUserId())
                .productType(orderCreateRequest.getProductType())
                .productId(orderCreateRequest.getProductId())
                .quantity(orderCreateRequest.getQuantity())
                .isOrdered(false)
                .build();

        orderRepository.save(order);
    }

    // 주문서 확인
    @Transactional(readOnly = true)
    public Order chekOrder(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("주문서가 존재하지 않습니다."));

        if (order.getIsOrdered()) {
            throw new RuntimeException("이미 결제 완료된 주문서입니다,");
        }

        return order;
    }

    // 주문서 업데이트
    @Transactional
    public void updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문서가 존재하지 않습니다."));

        order.updateOrder(orderUpdateRequest);

        orderRepository.save(order);
    }

    // 주문서 삭제
    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
