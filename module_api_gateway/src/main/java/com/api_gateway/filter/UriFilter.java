package com.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

@Component
public class UriFilter extends AbstractGatewayFilterFactory<UriFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(UriFilter.class);

    public static final String PRODUCT_TYPE_HEADER = "Product-Type";
    public static final String PRODUCT_TYPE_NORMAL = "normal";
    public static final String PRODUCT_TYPE_RESERVED = "reserved";


    public static class Config {}

    public UriFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String productType = request.getHeaders().get(PRODUCT_TYPE_HEADER).get(0);

            ServerHttpRequest modifiedRequest = null;

            if (StringUtils.hasText(productType)) {
                if (PRODUCT_TYPE_NORMAL.equals(productType)) {
                    modifiedRequest = request.mutate().path("/api/products/create").build();
                } else if (PRODUCT_TYPE_RESERVED.equals(productType)) {
                    modifiedRequest = request.mutate().path("/api/reservedProducts/create").build();
                }
            }

            ServerWebExchange modifiedExchange =
                    exchange.mutate().request(Objects.requireNonNull(modifiedRequest)).build();

            return chain.filter(modifiedExchange);
        };
    }
}
