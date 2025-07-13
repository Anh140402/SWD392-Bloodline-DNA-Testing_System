package com.adntest.adn_test_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AuthException("Account not found"));
    }

    public Account updateAccount(String id, Account account) {
        Account existingAccount = getAccountById(id);
        
        if (!existingAccount.getUsername().equals(account.getUsername()) 
                && accountRepository.existsByUsername(account.getUsername())) {
            throw new AuthException("Username already exists");
        }
        if (!existingAccount.getEmail().equals(account.getEmail()) 
                && accountRepository.existsByEmail(account.getEmail())) {
            throw new AuthException("Email already exists");
        }

        account.setUserId(id);
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        } else {
            account.setPassword(existingAccount.getPassword());
        }

        return accountRepository.save(account);
    }

    public void deleteAccount(String id) {
        if (!accountRepository.existsById(id)) {
            throw new AuthException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    public Account getProfile(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("Account not found"));
    }

    public Account updateProfile(String username, Account account) {
        Account existingAccount = getProfile(username);
        
        if (!existingAccount.getEmail().equals(account.getEmail()) 
                && accountRepository.existsByEmail(account.getEmail())) {
            throw new AuthException("Email already exists");
        }

        account.setUserId(existingAccount.getUserId());
        account.setUsername(existingAccount.getUsername());
        account.setRoles(existingAccount.getRoles());
        
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        } else {
            account.setPassword(existingAccount.getPassword());
        }

        return accountRepository.save(account);
    }
} 