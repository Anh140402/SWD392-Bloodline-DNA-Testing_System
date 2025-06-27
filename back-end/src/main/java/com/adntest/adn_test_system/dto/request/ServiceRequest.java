package com.adntest.adn_test_system.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceRequest {
    private String serviceName;
    private String description;
    private BigDecimal price;
    private String serviceType;
}
