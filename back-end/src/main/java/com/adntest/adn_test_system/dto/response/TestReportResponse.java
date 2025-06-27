package com.adntest.adn_test_system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestReportResponse {
    private String testReportId;
    private String testOrderId;
    private String result;
    private LocalDateTime createdAt;
    private Boolean isPositive;
}
