package com.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservedProductClient", url = "${internal.client.uri}")
public interface ReservedProductClient {

    @GetMapping("/api/internal/reserved-products/{reservedProductId}/get-price")
    Integer getReservedProductPrice(@PathVariable("reservedProductId") String reservedProductId);

}
