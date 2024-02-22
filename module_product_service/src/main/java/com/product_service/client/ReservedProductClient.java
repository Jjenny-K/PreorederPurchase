package com.product_service.client;

import com.core.dto.response.ProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "reservedProductClient", url = "${internal.client.uri}")
public interface ReservedProductClient {

    @GetMapping("/api/internal/reservedProducts/getReservedProductList")
    List<ProductListResponse> getReservedProductList();

}
