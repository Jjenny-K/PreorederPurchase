package com.product_service.service;

import com.product_service.entity.Product;
import com.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalProductService {

    private static final Logger logger = LoggerFactory.getLogger(InternalProductService.class);

    private final ProductRepository productRepository;

    // 일반 상품 가격 조회
    public Integer getProductPrice(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

        return product.getPrice();
    }

}
