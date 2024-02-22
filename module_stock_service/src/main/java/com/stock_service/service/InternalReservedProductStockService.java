package com.stock_service.service;

import com.stock_service.dto.request.ReservedProductStockCreateRequest;
import com.stock_service.entity.ReservedProductStock;
import com.stock_service.reposotiory.ReservedProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalReservedProductStockService {

    private static final Logger logger = LoggerFactory.getLogger(InternalReservedProductStockService.class);

    private final ReservedProductStockRepository reservedProductStockRepository;

    // 예약 상품 재고 등록
    @Transactional
    public void createReservedProductStock(ReservedProductStockCreateRequest reservedProductStockCreateRequest) {
        ReservedProductStock reservedProductStock = ReservedProductStock.builder()
                .productId(reservedProductStockCreateRequest.getReservedProductId())
                .stock(reservedProductStockCreateRequest.getStock())
                .build();

        reservedProductStockRepository.save(reservedProductStock);
    }

    // 예약 상품 재고 조회
    @Transactional(readOnly = true)
    public Integer getReservedProductStock(Long reservedProductId) {
        ReservedProductStock reservedProductStock =
                reservedProductStockRepository.findByProductId(reservedProductId)
                .orElseThrow(() -> new RuntimeException("해당 상품의 재고가 존재하지 않습니다."));

        return reservedProductStock.getStock();
    }

}
