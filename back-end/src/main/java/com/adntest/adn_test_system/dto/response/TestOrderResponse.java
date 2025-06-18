package com.adntest.adn_test_system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestOrderResponse {
    private String id;
    private String sampleId;
    private String accountId;
    private String serviceId;
    private LocalDateTime requestDate;
    private LocalDateTime preferredDate;
    private String requestType;
    private String status;  // "pending", "confirmed", "completed", "cancelled"
    private Boolean isOnlyCase;
    private String note;
}
