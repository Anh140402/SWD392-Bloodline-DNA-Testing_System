    package com.adntest.adn_test_system.controller;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.web.bind.annotation.*;

    import com.adntest.adn_test_system.dto.request.KitRequest;
    import com.adntest.adn_test_system.dto.response.KitResponse;
    import com.adntest.adn_test_system.service.KitService;

    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.security.SecurityRequirement;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;

    @RestController
    @RequestMapping("/api/v1/kits")
    @RequiredArgsConstructor
    @Tag(name = "Kit", description = "DNA Testing Kit management APIs")
    public class KitController {
        private final KitService kitService;

        @GetMapping
        @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
        @Operation(summary = "Get all kits (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<Page<KitResponse>> getAllKits(Pageable pageable) {
            return ResponseEntity.ok(kitService.getAllKits(pageable));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get kit by ID", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<KitResponse> getKitById(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
            return ResponseEntity.ok(kitService.getKitById(id, userDetails));
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
        @Operation(summary = "Create new kit (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<KitResponse> createKit(@Valid @RequestBody KitRequest request) {
            return ResponseEntity.ok(kitService.createKit(request));
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
        @Operation(summary = "Update kit (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<KitResponse> updateKit(@PathVariable String id, @Valid @RequestBody KitRequest request) {
            return ResponseEntity.ok(kitService.updateKit(id, request));
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Delete kit (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<Void> deleteKit(@PathVariable String id) {
            kitService.deleteKit(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/code/{code}")
        @Operation(summary = "Get kit by code", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<KitResponse> getKitByCode(@PathVariable String code, @AuthenticationPrincipal UserDetails userDetails) {
            return ResponseEntity.ok(kitService.getKitByCode(code, userDetails));
        }

        @GetMapping("/test-order/{testOrderId}")
        @Operation(summary = "Get kit by test order ID", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<KitResponse> getKitByTestOrder(@PathVariable String testOrderId, @AuthenticationPrincipal UserDetails userDetails) {
            return ResponseEntity.ok(kitService.getKitByTestOrder(testOrderId, userDetails));
        }

        @PostMapping("/generate")
        @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
        @Operation(summary = "Generate kit for test order (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
        public ResponseEntity<KitResponse> generateKitForTestOrder(@RequestParam String testOrderId) {
            return ResponseEntity.ok(kitService.generateKitForTestOrder(testOrderId));
        }
    }

