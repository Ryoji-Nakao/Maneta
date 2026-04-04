package com.nakao.maneta.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleResponse {
    private Long id;
    private String title;
    private String body;
    private boolean published;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String authorUsername;
}
