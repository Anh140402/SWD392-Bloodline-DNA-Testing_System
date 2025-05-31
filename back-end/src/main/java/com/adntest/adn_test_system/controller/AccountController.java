package com.adntest.adn_test_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adntest.adn_test_system.dto.request.LoginRequest;
import com.adntest.adn_test_system.dto.response.AuthResponse;
import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.service.AccountService;
import com.adntest.adn_test_system.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Account", description = "Account management APIs")
public class AccountController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    @PostMapping("/api/v1/auth/login")
    @Operation(summary = "Login with username and password")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/api/v1/auth/register")
    @Operation(summary = "Register new account")
    public ResponseEntity<Account> register(@Valid @RequestBody Account account) {
        return ResponseEntity.ok(authenticationService.register(account));
    }

    @GetMapping("/api/v1/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all accounts (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<Account>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable));
    }

    @GetMapping("/api/v1/accounts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get account by ID (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Account> getAccountById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("/api/v1/accounts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update account (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Account> updateAccount(@PathVariable String id, @Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/api/v1/accounts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete account (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/v1/profile")
    @Operation(summary = "Get current user profile", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Account> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(accountService.getProfile(userDetails.getUsername()));
    }

    @PutMapping("/api/v1/profile")
    @Operation(summary = "Update current user profile", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Account> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateProfile(userDetails.getUsername(), account));
    }
}