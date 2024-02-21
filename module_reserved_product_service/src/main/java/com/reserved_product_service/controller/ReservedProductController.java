package com.reserved_product_service.controller;

import com.reserved_product_service.dto.request.ReservedProductCreateRequest;
import com.reserved_product_service.dto.response.ReservedProductResponse;
import com.reserved_product_service.entity.ReservedProduct;
import com.reserved_product_service.service.ReservedProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservedProducts")
public class ReservedProductController {

    private final ReservedProductService reservedProductService;

    // 상품 등록
    @PostMapping()
    public ResponseEntity<?> create(HttpServletRequest httpServletRequest,
                                    @Valid @RequestBody ReservedProductCreateRequest reservedProductCreateRequest) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        String productTitle = reservedProductService.create(authorizedUserId, reservedProductCreateRequest);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", productTitle + "-> 상픔 등록 성공");

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    // 예약 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") String productId) {
        ReservedProduct reservedProduct =
                reservedProductService.getReservedProduct(Long.valueOf(productId));

        return new ResponseEntity<>(ReservedProductResponse.from(reservedProduct), HttpStatus.OK);
    }

}
