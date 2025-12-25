package com.crypto.exchange.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.crypto.exchange.gateway")
public class CryptoExchangeGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoExchangeGatewayApplication.class, args);
    }
}
