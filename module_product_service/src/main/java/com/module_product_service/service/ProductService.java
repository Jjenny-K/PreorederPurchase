package com.module_product_service.service;

import com.module_core.entity.type.ProductType;
import com.module_product_service.dto.request.ProductCreateRequest;
import com.module_product_service.entity.Product;
import com.module_product_service.entity.ReservedProduct;
import com.module_product_service.repository.ProductRepository;
import com.module_product_service.repository.ReservedProductRepository;
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
    private final ReservedProductRepository reservedProductRepository;

    // 상품 등록
    @Transactional
    public String create(Long authorizedUserId, ProductCreateRequest productCreateRequest) {
        if (productCreateRequest.getProductType().equals(ProductType.NORMAL)) {
            Product product = Product.builder()
                    .userId(authorizedUserId)
                    .title(productCreateRequest.getTitle())
                    .content(productCreateRequest.getContent())
                    .price(productCreateRequest.getPrice())
                    .build();

            productRepository.save(product);

            return product.getTitle();
        } else {
            if (productCreateRequest.getReservedStart() == null | productCreateRequest.getReservedEnd() == null) {
                throw new RuntimeException("예약 시간이 비어있습니다.");
            }

            ReservedProduct reservedProduct = ReservedProduct.builder()
                    .userId(authorizedUserId)
                    .title(productCreateRequest.getTitle())
                    .content(productCreateRequest.getContent())
                    .price(productCreateRequest.getPrice())
                    .reservedStart(productCreateRequest.getReservedStart())
                    .reservedEnd(productCreateRequest.getReservedEnd())
                    .build();

            reservedProductRepository.save(reservedProduct);

            return reservedProduct.getTitle();
        }
    }

    // 일반 상품 목록 조회
    @Transactional(readOnly = true)
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    // 에약 상품 목록 조회
    @Transactional(readOnly = true)
    public List<ReservedProduct> getReservedProductList() {
        return reservedProductRepository.findAll();
    }

    // 일반 상품 상세 조회
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
    }

    // 예약 상품 상세 조회
    @Transactional(readOnly = true)
    public ReservedProduct getReservedProduct(Long productId) {
        return reservedProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
    }

}
