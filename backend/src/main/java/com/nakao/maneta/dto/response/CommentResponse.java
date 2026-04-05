package com.nakao.maneta.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private String authorUsername;
}
