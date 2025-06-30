package com.adntest.adn_test_system.controller;

import com.adntest.adn_test_system.dto.request.TestOrderRequest;
import com.adntest.adn_test_system.dto.response.TestOrderResponse;
import com.adntest.adn_test_system.service.TestOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test-orders")
@RequiredArgsConstructor
@Tag(name = "Test Order", description = "DNA Test Order management APIs")
public class TestOrderController {
    private final TestOrderService testOrderService;

    @GetMapping
 //   @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get all test orders (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<TestOrderResponse>> getAllTestOrders(Pageable pageable) {
        return ResponseEntity.ok(testOrderService.getAllTestOrders(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get test order by ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<TestOrderResponse> getTestOrderById(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(testOrderService.getTestOrderById(id, userDetails));
    }

    @PostMapping
    @Operation(summary = "Create new test order", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<TestOrderResponse> createTestOrder(
            @Valid @RequestBody TestOrderRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(testOrderService.createTestOrder(request, userDetails));
    }

    @PutMapping("/{id}")
  //  @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Update test order (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<TestOrderResponse> updateTestOrder(
            @PathVariable String id,
            @Valid @RequestBody TestOrderRequest request) {
        return ResponseEntity.ok(testOrderService.updateTestOrder(id, request));
    }

    @DeleteMapping("/{id}")
  //  @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete test order (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteTestOrder(@PathVariable String id) {
        testOrderService.deleteTestOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-orders")
    @Operation(summary = "Get current user's test orders", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<TestOrderResponse>> getMyTestOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        return ResponseEntity.ok(testOrderService.getTestOrdersByUser(userDetails.getUsername(), pageable));
    }

    @GetMapping("/status/{status}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get test orders by status (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<TestOrderResponse>> getTestOrdersByStatus(
            @PathVariable String status,
            Pageable pageable) {
        return ResponseEntity.ok(testOrderService.getTestOrdersByStatus(status, pageable));
    }

    @PatchMapping("/{id}/status")
 //   @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Update test order status (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<TestOrderResponse> updateTestOrderStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ResponseEntity.ok(testOrderService.updateTestOrderStatus(id, status));
    }
}

