package com.reserved_product_service.client;

import com.reserved_product_service.dto.request.ReservedProductStockCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "StockClient", url = "${internal.client.uri}")
public interface StockClient {

    @PostMapping("/api/internal/reserved-product-stocks")
    void createReservedProductStock(ReservedProductStockCreateRequest reservedProductStockCreateRequest);

}
