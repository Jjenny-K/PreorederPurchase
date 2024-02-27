package com.reserved_product_service.controller;

import com.core.dto.response.ProductListResponse;
import com.core.entity.type.ProductType;
import com.reserved_product_service.entity.ReservedProduct;
import com.reserved_product_service.service.InternalReservedProductService;
import com.reserved_product_service.service.ReservedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/reserved-products")
public class InternalReservedProductController {

    private final InternalReservedProductService internalReservedProductService;

    // 예약 상품 목록 조회
    @GetMapping("/get-reserved-product-list")
    public ResponseEntity<?> getReservedProductList() {
        List<ReservedProduct> reservedProducts = internalReservedProductService.getReservedProductList();

        return ResponseEntity.ok().body(castingReservedProductList(reservedProducts));
    }

    private List<ProductListResponse> castingReservedProductList(List<ReservedProduct> reservedProducts) {
        List<ProductListResponse> reservedProductList = new ArrayList<>();

        for (ReservedProduct rp : reservedProducts) {
            ProductListResponse pl = ProductListResponse.builder()
                    .title(rp.getTitle())
                    .price(rp.getPrice())
                    .productType(ProductType.RESERVED)
                    .createdAt(rp.getCreatedAt())
                    .build();

            reservedProductList.add(pl);
        }

        return reservedProductList;
    }

    // 예약 상품 가격 조회
    @GetMapping("/{reservedProductId}/get-price")
    public ResponseEntity<?> findReservedProduct(@PathVariable("reservedProductId") String reservedProductId) {
        Integer reservedProductPrice =
                internalReservedProductService.getReservedProductPrice(Long.valueOf(reservedProductId));

        return ResponseEntity.ok().body(reservedProductPrice);
    }

}
