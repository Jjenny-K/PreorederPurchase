package com.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stockClient", url = "${internal.client.uri}")
public interface StockClient {

    @GetMapping("/api/internal/productStocks/{productId}")
    Integer getProductStock(@PathVariable("productId") String productId);

    @GetMapping("/api/internal/reservedProductStocks/{reservedProductId}")
    Integer getReservedProductStock(@PathVariable("reservedProductId") String reservedProductId);

    @PostMapping("/api/internal/productStocks/{productId}/decreasedStock")
    void decreasedProductStock(@PathVariable("productId") String productId,
                               @RequestParam(name = "quantity") String quantity);

    @PostMapping("/api/internal/reservedProductStocks/{reservedProductId}/decreasedStock")
    void decreasedReservedProductStock(@PathVariable("reservedProductId") String reservedProductId,
                                       @RequestParam(name = "quantity") String quantity);

    @PostMapping("/api/internal/productStocks/{productId}/increasedStock")
    void increasedProductStock(@PathVariable("productId") String productId,
                               @RequestParam(name = "quantity") String quantity);

    @PostMapping("/api/internal/reservedProductStocks/{reservedProductId}/increasedStock")
    void increasedReservedProductStock(@PathVariable("reservedProductId") String reservedProductId,
                                       @RequestParam(name = "quantity") String quantity);

}
