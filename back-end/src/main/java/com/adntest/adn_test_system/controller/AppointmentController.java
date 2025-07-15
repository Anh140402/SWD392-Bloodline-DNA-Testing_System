package com.adntest.adn_test_system.controller;

import com.adntest.adn_test_system.dto.request.AppointmentRequest;
import com.adntest.adn_test_system.dto.response.AppointmentResponse;
import com.adntest.adn_test_system.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment", description = "Appointment management APIs")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get all appointments (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable String id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PostMapping
    @Operation(summary = "Create new appointment", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update appointment", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable String id, @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel appointment", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> cancelAppointment(@PathVariable String id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}