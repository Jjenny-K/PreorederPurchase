package com.payment_service.service;

import com.core.dto.response.OrderCheckResponse;
import com.payment_service.client.StockClient;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisLockService {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockService.class);

    @Value("${spring.redis.lock.wait-time}")
    private Long waitTime;

    @Value("${spring.redis.lock.lease-time}")
    private Long leaseTime;

    @Value("${spring.redis.lock-suffix}")
    private String LOCK_SUFFIX;

    private final RedissonClient redissonClient;
    private final StockClient stockClient;

    // 재고 감소 + redis 분산 락
    public void decreasedStock(OrderCheckResponse order) throws InterruptedException {
        RLock lock = redissonClient.getLock(order.getProductId().toString() + LOCK_SUFFIX);

        boolean available = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);

        if (!available) {
            logger.error("lock 획득 시도 실패");
            throw new RuntimeException();
        }

        try {
            logger.debug("lock 획득");

            switch (order.getProductType()) {
                case NORMAL ->
                        stockClient.decreasedProductStock(
                                String.valueOf(order.getProductId()), String.valueOf(order.getQuantity()));

                case RESERVED ->
                        stockClient.decreasedReservedProductStock(
                                String.valueOf(order.getProductId()), String.valueOf(order.getQuantity()));
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                logger.debug("lock 해제");

                lock.unlock();
            } else {
                logger.error("lock 소유하지 않은 스레드");
                throw new RuntimeException();
            }
        }
    }

}
