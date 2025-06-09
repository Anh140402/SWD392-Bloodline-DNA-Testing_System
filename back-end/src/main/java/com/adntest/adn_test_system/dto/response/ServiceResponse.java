package com.adntest.adn_test_system.dto.response;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String type;
}
