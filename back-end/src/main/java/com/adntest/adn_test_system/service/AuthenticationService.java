package com.adntest.adn_test_system.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adntest.adn_test_system.configuration.JwtUtil;
import com.adntest.adn_test_system.dto.request.LoginRequest;
import com.adntest.adn_test_system.dto.response.AuthResponse;
import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            Account account = accountRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new AuthException("User not found"));

            String accessToken = jwtUtil.generateAccessToken(account);
            String refreshToken = jwtUtil.generateRefreshToken(account);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .build();
        } catch (Exception e) {
            throw new AuthException("Invalid username or password");
        }
    }

    public Account register(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new AuthException("Username already exists");
        }
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new AuthException("Email already exists");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnabled(true);
        account.setAccountNonLocked(true);
        account.setAccountNonExpired(true);
        account.setCredentialsNonExpired(true);
        
        return accountRepository.save(account);
    }
} 