package com.adntest.adn_test_system.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret;
    private AccessToken accessToken = new AccessToken();
    private RefreshToken refreshToken = new RefreshToken();

    @Data
    public static class AccessToken {
        private long expiration;
    }

    @Data
    public static class RefreshToken {
        private long expiration;
    }
} 