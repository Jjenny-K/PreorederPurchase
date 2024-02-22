package com.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stockClient", url = "${internal.client.uri}")
public interface StockClient {

    @GetMapping("/api/internal/productStocks/{productId}")
    Integer getProductStock(@PathVariable("productId") String productId);

    @GetMapping("/api/internal/reservedProductStocks/{reservedProductId}")
    Integer getReservedProductStock(@PathVariable("reservedProductId") String reservedProductId);

}
