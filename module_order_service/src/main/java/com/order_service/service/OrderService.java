package com.order_service.service;

import com.order_service.entity.Order;
import com.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    // 주문 목록 조회
    @Transactional(readOnly = true)
    public List<Order> getOrderList(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // 주문 상세 조회
    @Transactional(readOnly = true)
    public Order getOrder(Long userId, Long orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("주문서가 존재하지 않습니다."));
    }

}
