package com.adntest.adn_test_system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestOrderResponse {
    private String testOrderId;
    private String sampleId;
    private String accountId;
    private String serviceId;
    private String appointmentId;
    private LocalDateTime testOrderDate;
    private LocalDateTime preferredDate;
    private String orderType; //home-collect, etc
    private String status;  // "pending", "confirmed", "completed", "cancelled"
    private Boolean isOnlyCase;
    private String note;
}
