package com.adntest.adn_test_system.dto.response;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceResponse {
    private String serviceId;
    private String serviceName;
    private String description;
    private BigDecimal price;
    private String serviceType;
}
