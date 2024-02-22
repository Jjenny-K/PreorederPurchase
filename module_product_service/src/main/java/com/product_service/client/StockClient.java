package com.product_service.client;

import com.product_service.dto.request.ProductStockCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "stockClient", url = "${internal.client.uri}")
public interface StockClient {

    @PostMapping("/api/internal/productStocks")
    void createProductStock(ProductStockCreateRequest productStockCreateRequest);

}
