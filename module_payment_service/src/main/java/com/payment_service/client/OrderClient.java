package com.payment_service.client;

import com.payment_service.dto.request.OrderCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderClient", url = "${internal.client.uri}")
public interface OrderClient {

    @PostMapping("/api/internal/orders")
    void createOrder(@RequestBody OrderCreateRequest orderCreateRequest);

}
