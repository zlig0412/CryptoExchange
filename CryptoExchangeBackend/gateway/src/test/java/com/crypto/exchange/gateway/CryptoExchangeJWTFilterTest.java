package com.crypto.exchange.gateway;

import java.security.cert.X509Certificate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CryptoExchangeJWTFilterTest {
    private CryptoExchangeJWTFilter filter;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private GatewayFilterChain chain;

    @Mock
    private ServerHttpRequest request;
    
    @Mock
    private ServerHttpResponse response;

    @Mock
    private MultiValueMap<String, HttpCookie> cookies;

    @Mock
    private HttpCookie cookie;

    @Mock
    private Claims jwtClaims;

    @Mock
    private X509Certificate x509Certificate;

    @BeforeEach
    public void setUp() {
        filter = new CryptoExchangeJWTFilter();
    }

    @Test
    public void testFilter_NoTokenInCookies_ReturnsUnauthorized() {
        when(exchange.getRequest()).thenReturn(request);
        when(request.getCookies()).thenReturn(cookies);
        when(cookies.getFirst("token")).thenReturn(null);
        when(exchange.getResponse()).thenReturn(response);
        when(response.setStatusCode(HttpStatus.UNAUTHORIZED)).thenReturn(true);
        when(response.setComplete()).thenReturn(Mono.empty());

        Mono<Void> result = filter.filter(exchange, chain);

        StepVerifier.create(result).verifyComplete();
        verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
        verify(response).setComplete();
        verify(chain, never()).filter(any());
    }
}
