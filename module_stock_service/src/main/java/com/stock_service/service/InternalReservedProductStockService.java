package com.stock_service.service;

import com.stock_service.dto.request.ReservedProductStockCreateRequest;
import com.stock_service.entity.ReservedProductStock;
import com.stock_service.reposotiory.ReservedProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalReservedProductStockService {

    private static final Logger logger = LoggerFactory.getLogger(InternalReservedProductStockService.class);

    private final ReservedProductStockRepository reservedProductStockRepository;
    private final StringRedisTemplate redisTemplate;

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

    // 예약 상품 재고 감소
    @Transactional
    public void decreasedReservedProductStock(Long productId, Integer quantity) {
        ReservedProductStock reservedProductStock =
                reservedProductStockRepository.findByProductId(productId)
                        .orElseThrow(() -> new RuntimeException("해당 상품의 재고가 존재하지 않습니다."));

        if (reservedProductStock.getStock() == 0) {
            throw new RuntimeException("해당 상품의 재고가 존재하지 않습니다.");
        }

        if (reservedProductStock.getStock() < quantity) {
            throw new RuntimeException("해당 상품의 재고가 부족합니다.");
        }

        Integer updatedStock = reservedProductStock.getStock() - quantity;

        reservedProductStock.updateStock(updatedStock);

//        // 예약 상품 재고 감소 + redis
//        String strProductId = String.valueOf(productId);
//        String strStock = String.valueOf(reservedProductStock.getStock());
//
//        ValueOperations<String, String> stockOperations = redisTemplate.opsForValue();
//        stockOperations.set(strProductId, strStock);
//
//        Integer updatedStock = redisTemplate.opsForValue().decrement(strProductId, quantity).intValue();
//
//        reservedProductStock.updateStock(updatedStock <= 0 ? 0 : updatedStock);
    }

}
