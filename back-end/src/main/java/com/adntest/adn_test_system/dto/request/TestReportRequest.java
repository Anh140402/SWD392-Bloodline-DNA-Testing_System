package com.adntest.adn_test_system.dto.request;

import lombok.Data;

@Data
public class TestReportRequest {
    private String requestId;  // refers to TestOrder.id
    private String result;
    private Boolean isPositive;
}
