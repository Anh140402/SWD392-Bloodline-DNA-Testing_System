package com.adntest.adn_test_system.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private String testOrderId;  // refers to TestOrder.testRequestId
    private LocalDateTime scheduleDate;
    private String location;
    private String accountId;
}
