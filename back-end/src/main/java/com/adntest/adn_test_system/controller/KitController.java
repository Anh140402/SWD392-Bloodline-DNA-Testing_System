package com.adntest.adn_test_system.controller;

import com.adntest.adn_test_system.dto.request.KitRequest;
import com.adntest.adn_test_system.dto.response.KitResponse;
import com.adntest.adn_test_system.service.KitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kits")
@RequiredArgsConstructor
@Tag(name = "Kit", description = "DNA Testing Kit management APIs")
public class KitController {
    private final KitService kitService;

    @GetMapping
    @Operation(summary = "Get all kits (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<List<KitResponse>> getAllKits() {
        return ResponseEntity.ok(kitService.getAllKits());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get kit by ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<KitResponse> getKitById(@PathVariable String id) {
        return ResponseEntity.ok(kitService.getKitById(id));
    }

    @PostMapping
    @Operation(summary = "Create new kit (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<KitResponse> createKit(@Valid @RequestBody KitRequest request) {
        return ResponseEntity.ok(kitService.createKit(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update kit (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<KitResponse> updateKit(@PathVariable String id, @Valid @RequestBody KitRequest request) {
        return ResponseEntity.ok(kitService.updateKit(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete kit (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteKit(@PathVariable String id) {
        kitService.deleteKit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get kit by code", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<KitResponse> getKitByCode(@PathVariable String code) {
        return ResponseEntity.ok(kitService.getKitByCode(code));
    }

    @GetMapping("/test-order/{testOrderId}")
    @Operation(summary = "Get kit by test order ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<KitResponse> getKitByTestOrder(@PathVariable String testOrderId) {
        return ResponseEntity.ok(kitService.getKitByTestOrder(testOrderId));
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate kit for test order (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<KitResponse> generateKitForTestOrder(@RequestParam String testOrderId) {
        return ResponseEntity.ok(kitService.generateKitForTestOrder(testOrderId));
    }
}