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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class KitService {
    private final KitRepository kitRepository;
    private final TestOrderRepository testOrderRepository;
    private final AccountRepository accountRepository;

    public Page<KitResponse> getAllKits(Pageable pageable) {
        return kitRepository.findAll(pageable).map(this::toResponse);
    }

    public KitResponse getKitById(String id, UserDetails userDetails) {
        Kit kit = kitRepository.findById(id).orElseThrow(() -> new AuthException("Kit not found"));
        validateAccess(kit.getTestOrder().getAccount().getUserId(), userDetails);
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

    public KitResponse getKitByCode(String code, UserDetails userDetails) {
        Kit kit = kitRepository.findByCode(code).orElseThrow(() -> new AuthException("Kit not found"));
        validateAccess(kit.getTestOrder().getAccount().getUserId(), userDetails);
        return toResponse(kit);
    }

    public KitResponse getKitByTestOrder(String testOrderId, UserDetails userDetails) {
        TestOrder testOrder = testOrderRepository.findById(testOrderId)
                .orElseThrow(() -> new AuthException("Test order not found"));

        if (!testOrder.getAccount().getUserId().equals(userDetails.getUsername())) {
            throw new AuthException("Unauthorized access to test order's kit");
        }

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

    private void validateAccess(String ownerId, UserDetails userDetails) {
        if (!userDetails.getUsername().equals(ownerId) &&
                userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_STAFF"))) {
            throw new AuthException("Unauthorized access");
        }
    }
}
