package com.payment_service.client;

import com.core.dto.response.OrderCheckResponse;
import com.payment_service.dto.request.OrderCreateRequest;
import com.payment_service.dto.request.OrderUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "orderClient", url = "${internal.client.uri}")
public interface OrderClient {

    @PostMapping("/api/internal/orders")
    void createOrder(@RequestBody OrderCreateRequest orderCreateRequest);

    @GetMapping("/api/internal/orders/{orderId}")
    OrderCheckResponse getOrder(@PathVariable("orderId") String orderId);

    @PutMapping("/api/internal/orders/{orderId}")
    void updateOrder(@PathVariable("orderId") String orderId,
                     @RequestBody OrderUpdateRequest orderUpdateRequest);

    @DeleteMapping("/api/internal/orders/{orderId}")
    void deleteOrder(@PathVariable("orderId") String orderId);

}
