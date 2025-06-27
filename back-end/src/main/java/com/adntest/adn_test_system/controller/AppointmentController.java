package com.adntest.adn_test_system.controller;

import com.adntest.adn_test_system.dto.request.AppointmentRequest;
import com.adntest.adn_test_system.dto.response.AppointmentResponse;
import com.adntest.adn_test_system.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment", description = "Appointment management APIs")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get all appointments (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<AppointmentResponse>> getAllAppointments(Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAllAppointments(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PostMapping
    @Operation(summary = "Create new appointment", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request, @AuthenticationPrincipal UserDetails userDetails) {
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

//    @GetMapping("/my-appointments")
//    @Operation(summary = "Get current user's appointments", security = @SecurityRequirement(name = "Bearer Authentication"))
//    public ResponseEntity<Page<AppointmentResponse>> getMyAppointments(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
//        return ResponseEntity.ok(appointmentService.getAppointmentsByUsername(userDetails.getUsername(), pageable));
//    }

    @GetMapping("/test-order/{testOrderId}")
    @Operation(summary = "Get appointment by test order ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Optional<AppointmentResponse>> getAppointmentByTestOrder(@PathVariable String testOrderId) {
        return ResponseEntity.ok(appointmentService.getAppointmentByTestOrderId(testOrderId));
    }

//    @GetMapping("/upcoming")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
//    @Operation(summary = "Get upcoming appointments (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
//    public ResponseEntity<Page<AppointmentResponse>> getUpcomingAppointments(Pageable pageable) {
//        return ResponseEntity.ok(appointmentService.getUpcomingAppointments(pageable));
//    }
}

