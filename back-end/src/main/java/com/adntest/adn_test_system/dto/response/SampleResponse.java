package com.adntest.adn_test_system.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SampleResponse {
    private String sampleId;
    private String sampleType;
}
