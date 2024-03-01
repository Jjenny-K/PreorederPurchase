package com.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reservedProductStockClient", url = "${internal.client.uri}")
public interface ReservedProductStockClient {

    @GetMapping("/api/internal/reserved-product-stocks/{reservedProductId}")
    Integer getReservedProductStock(@PathVariable("reservedProductId") String reservedProductId);

    @PostMapping("/api/internal/reserved-product-stocks/{reservedProductId}/decreased-stock")
    void decreasedReservedProductStock(@PathVariable("reservedProductId") String reservedProductId,
                                       @RequestParam(name = "quantity") String quantity);

    @PostMapping("/api/internal/reserved-product-stocks/{reservedProductId}/increased-stock")
    void increasedReservedProductStock(@PathVariable("reservedProductId") String reservedProductId,
                                       @RequestParam(name = "quantity") String quantity);

}
