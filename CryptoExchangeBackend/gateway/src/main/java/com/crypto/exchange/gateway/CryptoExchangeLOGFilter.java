package com.crypto.exchange.gateway;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
@Order(1)
public class CryptoExchangeLOGFilter implements GlobalFilter , Ordered{

    private static final Logger log = LoggerFactory.getLogger(CryptoExchangeLOGFilter.class);
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Instant startTime = Instant.now();
        log.info("CryptoExchangeLOGFilter: " + exchange.getRequest().getPath());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> 
        log.info(String.format("CryptoExchangeLOGFilter: " + exchange.getResponse().getStatusCode() +
        ". And it cost %d ms", 
        Instant.now().toEpochMilli()-startTime.toEpochMilli()))));
    }

    @Override
    public int getOrder() {
        return 1;
    }
    
}
