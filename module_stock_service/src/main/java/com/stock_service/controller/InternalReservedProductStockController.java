package com.stock_service.controller;

import com.stock_service.dto.request.ReservedProductStockCreateRequest;
import com.stock_service.service.InternalReservedProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/reservedProductStocks")
public class InternalReservedProductStockController {

    private final InternalReservedProductStockService internalReservedProductStockService;

    // 예약 상품 재고 등록
    @PostMapping()
    public ResponseEntity<?> createProductStock(@RequestBody ReservedProductStockCreateRequest reservedProductStockCreateRequest) {
        internalReservedProductStockService.createReservedProductStock(reservedProductStockCreateRequest);

        return ResponseEntity.ok().build();
    }

    // 예약 상품 재고 조회
    @GetMapping("/{reservedProductId}")
    public ResponseEntity<?> getProductStock(@PathVariable("reservedProductId") String reservedProductId) {
        return ResponseEntity.ok().body(
                internalReservedProductStockService.getReservedProductStock(Long.parseLong(reservedProductId)));
    }

}
