package com.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productClient", url = "${internal.client.uri}")
public interface ProductClient {

    @GetMapping("/api/internal/products/{productId}/get-price")
    Integer getProductPrice(@PathVariable("productId") String productId);

}
