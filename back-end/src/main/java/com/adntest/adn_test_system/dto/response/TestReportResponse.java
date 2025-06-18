package com.adntest.adn_test_system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestReportResponse {
    private String id;
    private String requestId;
    private String result;
    private LocalDateTime createdAt;

}
