package com.product_service.service;

import com.core.dto.request.ProductCreateRequest;
import com.product_service.entity.Product;
import com.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    // 상품 등록
    @Transactional
    public String create(Long authorizedUserId, ProductCreateRequest productCreateRequest) {
        Product product = Product.builder()
                .userId(authorizedUserId)
                .title(productCreateRequest.getTitle())
                .content(productCreateRequest.getContent())
                .price(productCreateRequest.getPrice())
                .build();

        productRepository.save(product);

        return product.getTitle();
    }

    // 일반 상품 목록 조회
    @Transactional(readOnly = true)
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    // 일반 상품 상세 조회
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
    }

}
