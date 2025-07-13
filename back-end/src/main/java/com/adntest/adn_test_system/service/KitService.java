package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.KitRequest;
import com.adntest.adn_test_system.dto.response.KitResponse;
import com.adntest.adn_test_system.entity.Kit;
import com.adntest.adn_test_system.entity.TestOrder;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;
import com.adntest.adn_test_system.repository.KitRepository;
import com.adntest.adn_test_system.repository.TestOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KitService {
    private final KitRepository kitRepository;
    private final TestOrderRepository testOrderRepository;
    private final AccountRepository accountRepository;

    public List<KitResponse> getAllKits() {
        return kitRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public KitResponse getKitById(String id) {
        Kit kit = kitRepository.findById(id).orElseThrow(() -> new AuthException("Kit not found"));
        return toResponse(kit);
    }

    public KitResponse createKit(KitRequest request) {
        TestOrder testOrder = testOrderRepository.findById(request.getTestOrderId())
                .orElseThrow(() -> new AuthException("Test order not found"));

        if (kitRepository.existsByTestOrder(testOrder)) {
            throw new AuthException("Kit already exists for this test order");
        }

        Kit kit = Kit.builder()
                .kitId(UUID.randomUUID().toString())
                .code(request.getCode())
                .testOrder(testOrder)
                .issuedAt(LocalDateTime.now())
                .build();

        return toResponse(kitRepository.save(kit));
    }

    public KitResponse updateKit(String id, KitRequest request) {
        Kit existingKit = kitRepository.findById(id)
                .orElseThrow(() -> new AuthException("Kit not found"));

        existingKit.setCode(request.getCode());
        return toResponse(kitRepository.save(existingKit));
    }

    public void deleteKit(String id) {
        if (!kitRepository.existsById(id)) {
            throw new AuthException("Kit not found");
        }
        kitRepository.deleteById(id);
    }

    public KitResponse getKitByCode(String code) {
        Kit kit = kitRepository.findByCode(code).orElseThrow(() -> new AuthException("Kit not found"));
        return toResponse(kit);
    }

    public KitResponse getKitByTestOrder(String testOrderId) {
        TestOrder testOrder = testOrderRepository.findById(testOrderId)
                .orElseThrow(() -> new AuthException("Test order not found"));
        Kit kit = kitRepository.findByTestOrder(testOrder)
                .orElseThrow(() -> new AuthException("Kit not found for this test order"));
        return toResponse(kit);
    }

    public KitResponse generateKitForTestOrder(String testOrderId) {
        TestOrder testOrder = testOrderRepository.findById(testOrderId)
                .orElseThrow(() -> new AuthException("Test order not found"));

        if (kitRepository.existsByTestOrder(testOrder)) {
            throw new AuthException("Kit already generated for this test order");
        }

        String generatedCode = "KIT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Kit kit = Kit.builder()
                .kitId(UUID.randomUUID().toString())
                .code(generatedCode)
                .testOrder(testOrder)
                .issuedAt(LocalDateTime.now())
                .build();

        return toResponse(kitRepository.save(kit));
    }

    private KitResponse toResponse(Kit kit) {
        return KitResponse.builder()
                .kitId(kit.getKitId())
                .code(kit.getCode())
                .testOrderId(kit.getTestOrder().getTestOrderId())
                .issuedAt(kit.getIssuedAt())
                .build();
    }
}