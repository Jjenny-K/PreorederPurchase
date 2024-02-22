package com.order_service.service;

import com.order_service.dto.request.OrderCreateRequest;
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

    // 결제 진입 초기 주문 등록
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

}
