package com.adntest.adn_test_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.adntest.adn_test_system.dto.request.FeedbackRequest;
import com.adntest.adn_test_system.dto.response.FeedbackResponse;
import com.adntest.adn_test_system.service.FeedbackService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
<<<<<<< Updated upstream
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> Stashed changes

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Feedback management APIs")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get all feedbacks (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedbacks(Pageable pageable) {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get feedback by ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable String id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PostMapping
    @Operation(summary = "Create new feedback", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<FeedbackResponse> createFeedback(@Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.ok(feedbackService.createFeedback(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update feedback", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<FeedbackResponse> updateFeedback(@PathVariable String id, @Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete feedback", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}