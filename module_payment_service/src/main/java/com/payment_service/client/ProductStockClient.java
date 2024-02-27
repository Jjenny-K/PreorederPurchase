package com.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "productStockClient", url = "${internal.client.uri}")
public interface ProductStockClient {

    @GetMapping("/api/internal/product-stocks/{productId}")
    Integer getProductStock(@PathVariable("productId") String productId);

    @PostMapping("/api/internal/product-stocks/{productId}/decreased-stock")
    void decreasedProductStock(@PathVariable("productId") String productId,
                               @RequestParam(name = "quantity") String quantity);

    @PostMapping("/api/internal/product-stocks/{productId}/increased-stock")
    void increasedProductStock(@PathVariable("productId") String productId,
                               @RequestParam(name = "quantity") String quantity);

}
