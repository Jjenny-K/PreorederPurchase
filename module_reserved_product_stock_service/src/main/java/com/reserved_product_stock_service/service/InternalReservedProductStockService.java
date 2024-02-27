package com.reserved_product_stock_service.service;

import com.reserved_product_stock_service.dto.request.ReservedProductStockCreateRequest;
import com.reserved_product_stock_service.entity.ReservedProductStock;
import com.reserved_product_stock_service.reposotiory.ReservedProductStockRepository;
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
    private final RedisStockService redisStockService;

    // 예약 상품 재고 등록
    @Transactional
    public void createReservedProductStock(ReservedProductStockCreateRequest reservedProductStockCreateRequest) {
        ReservedProductStock reservedProductStock = ReservedProductStock.builder()
                .productId(reservedProductStockCreateRequest.getReservedProductId())
                .stock(reservedProductStockCreateRequest.getStock())
                .build();

        reservedProductStockRepository.save(reservedProductStock);
    }

    // 예약 상품 재고 등록 to redis
    @Transactional
    public void setReservedProductStock(String reservedProductId) {
        ReservedProductStock reservedProductStock =
                reservedProductStockRepository.findByProductId(Long.valueOf(reservedProductId))
                        .orElseThrow(() -> new RuntimeException("해당 상품의 재고가 존재하지 않습니다."));

        redisStockService.setStock(reservedProductId, reservedProductStock.getStock().toString());
    }

    // 예약 상품 재고 조회
    @Transactional(readOnly = true)
    public Integer getReservedProductStock(String reservedProductId) {
        return Integer.valueOf(redisStockService.getStock(reservedProductId));
    }

    // 예약 상품 재고 감소
    @Transactional
    public void decreasedReservedProductStock(String reservedProductId, Long quantity) {
        Integer updatedStock = redisStockService.decreasedStock(reservedProductId, quantity).intValue();

        // 재고가 0이 되었을 때 재고 업데이트
        if (updatedStock == 0) {
            updatedReservedProductStock(Long.valueOf(reservedProductId), updatedStock);
        }
    }

    // 예약 상품 재고 업데이트 to DB
    @Transactional
    public void updatedReservedProductStock(Long reservedProductId, Integer updatedStock) {
        ReservedProductStock reservedProductStock =
                reservedProductStockRepository.findByProductId(reservedProductId)
                        .orElseThrow(() -> new RuntimeException("해당 상품의 재고가 존재하지 않습니다."));

        reservedProductStock.updateStock(updatedStock);
    }

    // 예약 상품 재고 증가
    @Transactional
    public void increasedReservedProductStock(String reservedProductId, Long quantity) {
        Integer updatedStock = redisStockService.increasedStock(reservedProductId, quantity).intValue();

        updatedReservedProductStock(Long.valueOf(reservedProductId), updatedStock);
    }

}
