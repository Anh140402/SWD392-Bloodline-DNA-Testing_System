package com.adntest.adn_test_system.configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.exception.AuthException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Account account) {
        return generateToken(createClaims(account), account.getUsername(), 
                jwtProperties.getAccessToken().getExpiration());
    }

    public String generateRefreshToken(Account account) {
        return generateToken(new HashMap<>(), account.getUsername(), 
                jwtProperties.getRefreshToken().getExpiration());
    }

    private String generateToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Map<String, Object> createClaims(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", account.getUserId());
        claims.put("role", account.getRoles());
        claims.put("email", account.getEmail());
        return claims;
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException("JWT token has expired");
        } catch (UnsupportedJwtException e) {
            throw new AuthException("JWT token is unsupported");
        } catch (MalformedJwtException e) {
            throw new AuthException("Invalid JWT token");
        } catch (SignatureException e) {
            throw new AuthException("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            throw new AuthException("JWT claims string is empty");
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }
} 