package com.reserved_product_stock_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisStockService {

    private static final Logger logger = LoggerFactory.getLogger(RedisStockService.class);

    private final StringRedisTemplate redisTemplate;

    // 재고 등록
    public void setStock(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 재고 조회
    public String getStock(String key) {
        String stock = redisTemplate.opsForValue().get(key);

        if (stock == null) {
            throw new RuntimeException("해당 상품의 재고가 존재하지 않습니다.");
        }

        return stock;
    }

    // 재고 감소
    public Long decreasedStock(String key, Long quantity) {
        Long savedStock = redisTemplate.opsForValue().decrement(key, quantity);

        if (savedStock < 0) {
            redisTemplate.opsForValue().set(key, "0");
            throw new RuntimeException("해당 상품의 재고가 부족합니다.");
        }

        return savedStock;
    }

    // 재고 증가
    public Long increasedStock(String key, Long quantity) {
        return redisTemplate.opsForValue().increment(key, quantity);
    }
}
