package com.reserved_product_service.service;

import com.reserved_product_service.client.StockClient;
import com.reserved_product_service.dto.request.ReservedProductCreateRequest;
import com.reserved_product_service.dto.request.ReservedProductStockCreateRequest;
import com.reserved_product_service.entity.ReservedProduct;
import com.reserved_product_service.repository.ReservedProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservedProductService {

    private static final Logger logger = LoggerFactory.getLogger(ReservedProductService.class);

    private final ReservedProductRepository reservedProductRepository;
    private final StockClient stockClient;

    // 예약 상품 등록
    @Transactional
    public String create(Long authorizedUserId, ReservedProductCreateRequest reservedProductCreateRequest) {
        if (reservedProductCreateRequest.getReservedStart() == null |
                reservedProductCreateRequest.getReservedEnd() == null) {
            throw new RuntimeException("예약 시간이 비어있습니다.");
        }

        ReservedProduct reservedProduct = ReservedProduct.builder()
                .userId(authorizedUserId)
                .title(reservedProductCreateRequest.getTitle())
                .content(reservedProductCreateRequest.getContent())
                .price(reservedProductCreateRequest.getPrice())
                .reservedStart(reservedProductCreateRequest.getReservedStart())
                .reservedEnd(reservedProductCreateRequest.getReservedEnd())
                .build();

        ReservedProduct savedReservedProduct = reservedProductRepository.save(reservedProduct);

        stockClient.createReservedProductStock(
                new ReservedProductStockCreateRequest(
                        savedReservedProduct.getId(), reservedProductCreateRequest.getStock()));

        return reservedProduct.getTitle();
    }

    // 예약 상품 상세 조회
    @Transactional(readOnly = true)
    public ReservedProduct getReservedProduct(Long reservedProductId) {
        return reservedProductRepository.findById(reservedProductId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
    }

}
