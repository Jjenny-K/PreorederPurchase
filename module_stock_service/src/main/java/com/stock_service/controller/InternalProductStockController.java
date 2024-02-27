package com.stock_service.controller;

import com.stock_service.dto.request.ProductStockCreateRequest;
import com.stock_service.service.InternalProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/productStocks")
public class InternalProductStockController {

    private final InternalProductStockService internalProductStockService;

    // 일반 상품 재고 등록
    @PostMapping()
    public ResponseEntity<?> createProductStock(@RequestBody ProductStockCreateRequest productStockCreateRequest) {
        internalProductStockService.createProductStock(productStockCreateRequest);

        return ResponseEntity.ok().build();
    }

    // 일반 상품 재고 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductStock(@PathVariable("productId") String productId) {
        return ResponseEntity.ok().body(internalProductStockService.getProductStock(Long.parseLong(productId)));
    }

    // 일반 상품 재고 감소
    @PostMapping("/{productId}/decreasedStock")
    public ResponseEntity<?> decreasedProductStock(@PathVariable("productId") String productId,
                                                   @RequestParam(name = "quantity") String quantity) {
        internalProductStockService.decreasedProductStock(
                Long.valueOf(productId), Integer.valueOf(quantity));

        return ResponseEntity.ok().build();
    }

    // 일반 상품 재고 증가
    @PostMapping("/{productId}/increasedStock")
    public ResponseEntity<?> increasedProductStock(@PathVariable("productId") String productId,
                                                   @RequestParam(name = "quantity") String quantity) {
        internalProductStockService.increasedProductStock(
                Long.valueOf(productId), Integer.valueOf(quantity));

        return ResponseEntity.ok().build();
    }

}
