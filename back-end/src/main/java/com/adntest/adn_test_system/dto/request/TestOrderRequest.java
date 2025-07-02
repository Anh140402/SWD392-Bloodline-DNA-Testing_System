package com.adntest.adn_test_system.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestOrderRequest {
    private String testOrderId;
    private String sampleId;
    private String accountId;
    private String serviceId;
    private LocalDateTime testOrderDate;
    private LocalDateTime preferredDate;
    private String orderType; //home-collect, etc
    private String status;  // "pending", "confirmed", "completed", "cancelled"
    private Boolean isOnlyCase;
    private String note;
    private String appointmentId; // optional, if appointment is needed
}
