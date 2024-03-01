package com.product_stock_service.service;

import com.product_stock_service.dto.request.ProductStockCreateRequest;
import com.product_stock_service.entity.ProductStock;
import com.product_stock_service.reposotiory.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalProductStockService {

    private static final Logger logger = LoggerFactory.getLogger(InternalProductStockService.class);

    private final ProductStockRepository productStockRepository;

    // 일반 상품 재고 등록
    @Transactional
    public void createProductStock(ProductStockCreateRequest productStockCreateRequest) {
        ProductStock productStock = ProductStock.builder()
                .productId(productStockCreateRequest.getProductId())
                .stock(productStockCreateRequest.getStock())
                .build();

        productStockRepository.save(productStock);
    }

    // 일반 상품 재고 조회
    @Transactional(readOnly = true)
    public Integer getProductStock(Long productId) {
        ProductStock productStock =
                productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품의 재고가 존재하지 않습니다."));

        return productStock.getStock();
    }

    // 일반 상품 재고 감소
    @Transactional
    public void decreasedProductStock(Long productId, Integer quantity) {
        ProductStock productStock =
                productStockRepository.findByProductId(productId)
                        .orElseThrow(() -> new RuntimeException("해당 상품의 재고가 존재하지 않습니다."));

        Integer updatedStock = productStock.getStock() - quantity;

        productStock.updateStock(updatedStock);
    }

    // 일반 상품 재고 증가
    @Transactional
    public void increasedProductStock(Long productId, Integer quantity) {
        ProductStock productStock =
                productStockRepository.findByProductId(productId)
                        .orElseThrow(() -> new RuntimeException("해당 상품의 재고가 존재하지 않습니다."));

        Integer updatedStock = productStock.getStock() + quantity;

        productStock.updateStock(updatedStock);
    }

}
