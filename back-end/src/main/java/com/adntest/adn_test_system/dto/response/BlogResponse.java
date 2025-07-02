package com.adntest.adn_test_system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class BlogResponse {
    private String blogId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorId;
    private String authorName;  // firstname + lastname for display
}

