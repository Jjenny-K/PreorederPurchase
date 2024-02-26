package com.payment_service.service;

import com.core.dto.response.OrderCheckResponse;
import com.core.entity.type.ProductType;
import com.payment_service.client.OrderClient;
import com.payment_service.client.ProductClient;
import com.payment_service.client.ReservedProductClient;
import com.payment_service.client.StockClient;
import com.payment_service.dto.request.EnterPaymentRequest;
import com.payment_service.dto.request.OrderCreateRequest;
import com.payment_service.dto.request.OrderUpdateRequest;
import com.payment_service.dto.request.PaymentCreateRequest;
import com.payment_service.entity.Payment;
import com.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final ProductClient productClient;
    private final ReservedProductClient reservedProductClient;
    private final StockClient stockClient;
    private final OrderClient orderClient;
//    private final RedisLockService redisLockService;

    // 일반 상품 결제 진입
    @Transactional
    public Integer enterPaymentProduct(Long authorizedUserId, Long productId,
                                       EnterPaymentRequest enterPaymentRequest) {
        // 결제 진입 실패 시나리오
        if (Math.random() <= 0.2) {
            throw new RuntimeException("결제 진입 실패");
        }

        // 구매 수량과 재고 비교
        Integer productStock = stockClient.getProductStock(String.valueOf(productId));

        if (productStock <= 0) {
            throw new RuntimeException("구매할 수 있는 상품이 없습니다.");
        }

        if (productStock < enterPaymentRequest.getQuantity()) {
            throw new RuntimeException("구매 수량은 상품의 재고를 넘을 수 없습니다.");
        }

        // 주문서 발행
        orderClient.createOrder(
                new OrderCreateRequest(
                        authorizedUserId, productId, ProductType.NORMAL, enterPaymentRequest.getQuantity()));

        // 총 결제 금액 반환
        Integer productPrice =
                productClient.getProductPrice(String.valueOf(productId));

        return productPrice * enterPaymentRequest.getQuantity();
    }

    // 예약 상품 결제 진입
    @Transactional
    public Integer enterPaymentReservedProduct(Long authorizedUserId, Long reservedProductId,
                                               EnterPaymentRequest enterPaymentRequest) {
        // 결제 진입 실패 시나리오
        if (Math.random() <= 0.2) {
            throw new RuntimeException("결제 진입 실패");
        }

        // 구매 수량과 재고 비교
        Integer reservedProductStock = stockClient.getReservedProductStock(String.valueOf(reservedProductId));

        if (reservedProductStock == 0) {
            throw new RuntimeException("구매할 수 있는 상품이 없습니다.");
        }

        if (reservedProductStock < enterPaymentRequest.getQuantity()) {
            throw new RuntimeException("구매 수량은 상품의 재고를 넘을 수 없습니다.");
        }

        // 주문서 발행
        orderClient.createOrder(
                new OrderCreateRequest(
                        authorizedUserId, reservedProductId, ProductType.RESERVED, enterPaymentRequest.getQuantity()));

        // 총 결제 금액 반환
        Integer reservedProductPrice =
                reservedProductClient.getReservedProductPrice(String.valueOf(reservedProductId));

        return reservedProductPrice * enterPaymentRequest.getQuantity();
    }

    // 상품 결제
    @Transactional
    public void payment(Long orderId, PaymentCreateRequest paymentCreateRequest) throws InterruptedException {
        OrderCheckResponse order = orderClient.getOrder(String.valueOf(orderId));

        // 결제 실패 시나리오
        if (Math.random() <= 0.2) {
            orderClient.deleteOrder(String.valueOf(orderId));

            throw new RuntimeException("결제 실패");
        }

        // 재고 감소
        switch (order.getProductType()) {
            case NORMAL ->
                    stockClient.decreasedProductStock(
                            String.valueOf(order.getProductId()), String.valueOf(order.getQuantity()));

            case RESERVED ->
                    stockClient.decreasedReservedProductStock(
                            String.valueOf(order.getProductId()), String.valueOf(order.getQuantity()));
        }
//        redisLockService.decreasedStock(order);

        // 결제서 발행
        Payment payment = Payment.builder()
                .userId(order.getUserId())
                .orderId(orderId)
                .totalPayment(paymentCreateRequest.getTotalPayment())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // 주문서 업데이트
        orderClient.updateOrder
                (String.valueOf(orderId), new OrderUpdateRequest(savedPayment.getId(), true));
    }

}
