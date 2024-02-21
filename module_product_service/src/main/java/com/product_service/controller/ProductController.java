package com.product_service.controller;

import com.core.dto.response.ProductListResponse;
import com.core.entity.type.ProductType;
import com.product_service.client.ReservedProductClient;
import com.product_service.dto.request.ProductCreateRequest;
import com.product_service.dto.response.ProductResponse;
import com.product_service.entity.Product;
import com.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ReservedProductClient reservedProductClient;

    // 상품 등록
    @PostMapping()
    public ResponseEntity<?> create(HttpServletRequest httpServletRequest,
                                    @Valid @RequestBody ProductCreateRequest productCreateRequest) {
        Long authorizedUserId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

        String productTitle = productService.create(authorizedUserId, productCreateRequest);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", productTitle + "-> 상픔 등록 성공");

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    // 상품 목록 조회
    @GetMapping()
    public ResponseEntity<List<ProductListResponse>> getProductTotalList() {
        List<ProductListResponse> productTotalList = new ArrayList<>();

        productTotalList.addAll(castingProductList(productService.getProductList()));
        productTotalList.addAll(reservedProductClient.getReservedProductList());

        productTotalList.sort(Comparator.comparing(ProductListResponse::getCreatedAt).reversed());

        return ResponseEntity.ok().body(productTotalList);
    }

    private List<ProductListResponse> castingProductList(List<Product> products) {
        List<ProductListResponse> reservedProductList = new ArrayList<>();

        for (Product p : products) {
            ProductListResponse pl = ProductListResponse.builder()
                    .title(p.getTitle())
                    .price(p.getPrice())
                    .productType(ProductType.NORMAL)
                    .createdAt(p.getCreatedAt())
                    .build();

            reservedProductList.add(pl);
        }

        return reservedProductList;
    }

    // 일반 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") String productId) {
        Product product = productService.getProduct(Long.valueOf(productId));

        return new ResponseEntity<>(ProductResponse.from(product), HttpStatus.OK);
    }

}
