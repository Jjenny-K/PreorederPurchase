package com.payment_service.service;

import com.core.entity.type.ProductType;
import com.payment_service.client.OrderClient;
import com.payment_service.client.ProductClient;
import com.payment_service.client.ReservedProductClient;
import com.payment_service.client.StockClient;
import com.payment_service.dto.request.EnterPaymentRequest;
import com.payment_service.dto.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final ProductClient productClient;
    private final ReservedProductClient reservedProductClient;
    private final StockClient stockClient;
    private final OrderClient orderClient;

    // 일반 상품 결제 진입
    @Transactional
    public Integer enterPaymentProduct(Long authorizedUserId, Long productId,
                                       EnterPaymentRequest enterPaymentRequest) {
        // 구매 수량과 재고 비교
        Integer productStock = stockClient.getProductStock(String.valueOf(productId));

        if (productStock == 0) {
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

}
