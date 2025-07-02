package com.adntest.adn_test_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KitRequest {
    @NotBlank(message = "Kit code is required")
    private String code;
    
    @NotBlank(message = "Test order ID is required")
    private String testOrderId;  // refers to TestOrder.testRequestId
}
