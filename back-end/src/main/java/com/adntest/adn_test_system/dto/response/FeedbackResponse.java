package com.adntest.adn_test_system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class FeedbackResponse {
    private String feedbackId;
    private String accountId;
    private String customerName;  // firstname + lastname for display
    private String testOrderId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}

