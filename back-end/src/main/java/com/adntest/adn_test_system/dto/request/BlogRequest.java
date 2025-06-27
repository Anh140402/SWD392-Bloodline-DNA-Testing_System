package com.adntest.adn_test_system.dto.request;

import lombok.Data;

@Data
public class BlogRequest {
    private String title;
    private String content;
    private String authorId;
}

