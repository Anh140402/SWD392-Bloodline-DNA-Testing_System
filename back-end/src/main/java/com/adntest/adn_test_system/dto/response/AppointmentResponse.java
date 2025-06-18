package com.adntest.adn_test_system.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private String appointmentId;
    private String requestId;
    private LocalDateTime scheduleDate;
    private String location;
    private String accountId;
}
