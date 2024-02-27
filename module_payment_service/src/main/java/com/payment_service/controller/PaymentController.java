package com.payment_service.controller;

import com.payment_service.dto.request.EnterPaymentRequest;
import com.payment_service.dto.request.PaymentCreateRequest;
import com.payment_service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 일반 상품 결제 진입
    @PostMapping("/products/{productId}")
    public ResponseEntity<?> enterPaymentProduct(HttpServletRequest httpServletRequest,
                                              @PathVariable("productId") String productId,
                                              @Valid @RequestBody EnterPaymentRequest enterPaymentRequest) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        Integer totalPayment =
                paymentService.enterPaymentProduct(
                        authorizedUserId, Long.valueOf(productId), enterPaymentRequest);

        PaymentCreateRequest paymentCreateRequest = new PaymentCreateRequest(totalPayment);

        return ResponseEntity.ok().body(paymentCreateRequest);
    }

    // 예약 상품 결제 진입
    @PostMapping("/reserved-products/{reservedProductId}")
    public ResponseEntity<?> enterPaymentReservedProduct(HttpServletRequest httpServletRequest,
                                                         @PathVariable("reservedProductId") String reservedProductId,
                                                         @Valid @RequestBody EnterPaymentRequest enterPaymentRequest) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        Integer totalPayment =
                paymentService.enterPaymentReservedProduct(
                        authorizedUserId, Long.valueOf(reservedProductId), enterPaymentRequest);

        PaymentCreateRequest paymentCreateRequest = new PaymentCreateRequest(totalPayment);

        return ResponseEntity.ok().body(paymentCreateRequest);
    }

    // 상품 결제
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<?> payment(@PathVariable("orderId") String orderId,
                                     @Valid @RequestBody PaymentCreateRequest paymentCreateRequest)
            throws InterruptedException {

        paymentService.payment(Long.valueOf(orderId), paymentCreateRequest);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "결제 성공");

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

}
