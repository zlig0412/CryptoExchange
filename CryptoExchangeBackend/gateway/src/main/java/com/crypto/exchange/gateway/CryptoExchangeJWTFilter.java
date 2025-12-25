package com.crypto.exchange.gateway;

import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
@Order(-100)
public class CryptoExchangeJWTFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(CryptoExchangeJWTFilter.class);

     public CryptoExchangeJWTFilter() {
        log.info("CryptoExchangeJWTFilter initialized");
    }
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String jwtToken = getTokenFromCookie(exchange);

        if(null == jwtToken || jwtToken.trim().isEmpty()){
            log.error("No JWT token found in cookies");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        Claims jwtClaims = null;
        try {
            jwtClaims = extractJWT(jwtToken);
        } catch (Exception e) {
            log.error("Error parsing JWT: {}", e.getMessage());
        }

        if(null == jwtClaims){
            log.error("Error parsing JWT: No JWT claims in token.");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        ServerWebExchange modifiedExchange = exchange.mutate()
            .request(exchange.getRequest().mutate()
                .header("CEX-User-ID", jwtClaims.getSubject())
                .header("CEX-User-Name", jwtClaims.get("name", String.class))
                .header("CEX-User-Roles", jwtClaims.get("roles", String.class))
                .build())
            .build();

        return chain.filter(modifiedExchange);
    }

    private String getTokenFromCookie(ServerWebExchange exchange) {
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        HttpCookie cookie = cookies.getFirst("token");
        return cookie != null ? cookie.getValue() : null;
    }
    
    private Claims extractJWT(String token)
    {
        try (InputStream is = CryptoExchangeJWTFilter.class.getResourceAsStream("/casdoor_x509.crt")) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate)cf.generateCertificate(is);
            return Jwts.parserBuilder()
            .setSigningKey(cert.getPublicKey())
            .build()
            .parseClaimsJwt(token)
            .getBody();
            
        } catch (Exception e) {
            throw new RuntimeException("Loading X.509 cert file failed. ", e);
        }
    }

}
