package com.adntest.adn_test_system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class AppointmentResponse {
    private String appointmentId;
    private LocalDateTime scheduleDate;
    private String location;
    private String accountId;
    private String testOrderId;
}
