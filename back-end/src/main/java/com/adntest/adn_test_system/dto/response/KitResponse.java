package com.adntest.adn_test_system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class KitResponse {
    private String kitId;
    private String code;
    private LocalDateTime issuedAt;
    private String testOrderId;
}
