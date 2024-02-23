package com.reserved_product_service.service;

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
public class InternalReservedProductService {

    private static final Logger logger = LoggerFactory.getLogger(InternalReservedProductService.class);

    private final ReservedProductRepository reservedProductRepository;

    // 에약 상품 목록 조회
    @Transactional(readOnly = true)
    public List<ReservedProduct> getReservedProductList() {
        return reservedProductRepository.findAll();
    }

    // 예약 상품 가격 조회
    @Transactional(readOnly = true)
    public Integer getReservedProductPrice(Long reservedProductId) {
        ReservedProduct reservedProduct = reservedProductRepository.findById(reservedProductId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

        return reservedProduct.getPrice();
    }

}
