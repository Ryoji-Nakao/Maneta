package com.nakao.maneta.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private LocalDateTime createdAt;
}
