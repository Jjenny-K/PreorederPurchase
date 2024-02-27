package com.product_service.controller;

import com.product_service.service.InternalProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/products")
public class InternalProductController {

    private final InternalProductService internalProductService;

    // 일반 상품 가격 조회
    @GetMapping("/{productId}/get-price")
    public ResponseEntity<?> getProductPrice(@PathVariable("productId") String productId) {
        Integer productPrice = internalProductService.getProductPrice(Long.valueOf(productId));

        return ResponseEntity.ok().body(productPrice);
    }

}
