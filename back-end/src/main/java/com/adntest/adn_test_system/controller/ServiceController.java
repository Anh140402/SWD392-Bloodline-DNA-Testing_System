package com.adntest.adn_test_system.controller;

import com.adntest.adn_test_system.dto.request.ServiceRequest;
import com.adntest.adn_test_system.dto.response.ServiceResponse;
import com.adntest.adn_test_system.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@Tag(name = "Service", description = "DNA Testing Service management APIs")
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping
    @Operation(summary = "Get all services")
    public ResponseEntity<Page<ServiceResponse>> getAllServices(Pageable pageable) {
        return ResponseEntity.ok(serviceService.getAllServices(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get service by ID")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable String id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PostMapping
 //   @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new service (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ServiceResponse> createService(@Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(serviceService.createService(request));
    }

    @PutMapping("/{id}")
 //   @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update service (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ServiceResponse> updateService(@PathVariable String id, @Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(serviceService.updateService(id, request));
    }

    @DeleteMapping("/{id}")
 //   @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete service (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{serviceType}")
    @Operation(summary = "Get services by type")
    public ResponseEntity<Page<ServiceResponse>> getServicesByType(@PathVariable String serviceType, Pageable pageable) {
        return ResponseEntity.ok(serviceService.getServicesByType(serviceType, pageable));
    }
}

