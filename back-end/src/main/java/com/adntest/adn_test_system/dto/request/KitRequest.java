package com.adntest.adn_test_system.dto.request;

import lombok.Data;

@Data
public class KitRequest {
    private String code;
    private String orderId;  // refers to TestOrder.id
}
