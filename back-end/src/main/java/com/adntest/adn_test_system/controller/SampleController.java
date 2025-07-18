package com.adntest.adn_test_system.controller;

import com.adntest.adn_test_system.dto.request.SampleRequest;
import com.adntest.adn_test_system.dto.response.SampleResponse;
import com.adntest.adn_test_system.service.SampleService;
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
@RequestMapping("/api/v1/samples")
@RequiredArgsConstructor
@Tag(name = "Sample", description = "DNA Sample management APIs")
public class SampleController {
    private final SampleService sampleService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get all samples (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<SampleResponse>> getAllSamples(Pageable pageable) {
        return ResponseEntity.ok(sampleService.getAllSamples(pageable));
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get sample by ID (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<SampleResponse> getSampleById(@PathVariable String id) {
        return ResponseEntity.ok(sampleService.getSampleById(id));
    }

    @PostMapping    
    //@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Create new sample (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<SampleResponse> createSample(@Valid @RequestBody SampleRequest request) {
        return ResponseEntity.ok(sampleService.createSample(request));
    }

    @PutMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Update sample (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<SampleResponse> updateSample(@PathVariable String id, @Valid @RequestBody SampleRequest request) {
        return ResponseEntity.ok(sampleService.updateSample(id, request));
    }

    @DeleteMapping("/{id}")
 //   @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete sample (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteSample(@PathVariable String id) {
        sampleService.deleteSample(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    @Operation(summary = "Get available sample types")
    public ResponseEntity<Page<SampleResponse>> getSampleTypes(Pageable pageable) {
        return ResponseEntity.ok(sampleService.getSampleTypes(pageable));
    }

    @GetMapping("/type/{sampleType}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get samples by type (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<SampleResponse>> getSamplesByType(@PathVariable String sampleType, Pageable pageable) {
        return ResponseEntity.ok(sampleService.getSamplesByType(sampleType, pageable));
    }
}

