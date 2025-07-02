package com.adntest.adn_test_system.dto.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    private String accountId;
    private String testOrderId;  // refers to TestOrder.testRequestId
    private Integer rating;
    private String comment;  // Optional comment
}

