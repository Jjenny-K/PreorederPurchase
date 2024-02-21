package com.reserved_product_service.service;

import com.core.entity.type.ProductType;
import com.reserved_product_service.dto.request.ProductCreateRequest;
import com.reserved_product_service.entity.ReservedProduct;
import com.reserved_product_service.repository.ReservedProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservedProductService {

    private static final Logger logger = LoggerFactory.getLogger(ReservedProductService.class);

    private final ReservedProductRepository reservedProductRepository;

    // 상품 등록
    @Transactional
    public String create(Long authorizedUserId, ProductCreateRequest productCreateRequest) {
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

    // 에약 상품 목록 조회
    @Transactional(readOnly = true)
    public List<ReservedProduct> getReservedProductList() {
        return reservedProductRepository.findAll();
    }

    // 예약 상품 상세 조회
    @Transactional(readOnly = true)
    public ReservedProduct getReservedProduct(Long productId) {
        return reservedProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
    }

}
