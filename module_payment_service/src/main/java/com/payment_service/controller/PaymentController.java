package com.payment_service.controller;

import com.payment_service.dto.request.EnterPaymentRequest;
import com.payment_service.dto.request.PaymentCreateRequest;
import com.payment_service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 일반 상품 결제 진입
    @PostMapping("/products/{productId}")
    public ResponseEntity<?> enterPaymentProduct(HttpServletRequest httpServletRequest,
                                              @PathVariable("productId") String productId,
                                              @RequestBody EnterPaymentRequest enterPaymentRequest) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        Integer totalPayment =
                paymentService.enterPaymentProduct(
                        authorizedUserId, Long.valueOf(productId), enterPaymentRequest);

        PaymentCreateRequest paymentCreateRequest = new PaymentCreateRequest(totalPayment);

        return ResponseEntity.ok().body(paymentCreateRequest);
    }

    // 예약 상품 결제 진입
    @PostMapping("/reservedProducts/{reservedProductId}")
    public ResponseEntity<?> enterPaymentReservedProduct(HttpServletRequest httpServletRequest,
                                                         @PathVariable("reservedProductId") String reservedProductId,
                                                         @RequestBody EnterPaymentRequest enterPaymentRequest) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        Integer totalPayment =
                paymentService.enterPaymentReservedProduct(
                        authorizedUserId, Long.valueOf(reservedProductId), enterPaymentRequest);

        PaymentCreateRequest paymentCreateRequest = new PaymentCreateRequest(totalPayment);

        return ResponseEntity.ok().body(paymentCreateRequest);
    }

}
