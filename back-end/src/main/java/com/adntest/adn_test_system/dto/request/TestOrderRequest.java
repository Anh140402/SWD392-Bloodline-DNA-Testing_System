package com.adntest.adn_test_system.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestOrderRequest {
    private String sampleId;
    private String accountId;
    private String serviceId;
    private LocalDateTime preferredDate;
    private String requestType;  // "home_collection", "facility_collection", "self_collection"
    private Boolean isOnlyCase;
    private String note;
}
