package com.nakao.maneta.controller;

import com.nakao.maneta.dto.response.UserResponse;
import com.nakao.maneta.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> selectUser(@PathVariable String username) {
        UserResponse userResponse = userService.selectUser(username);
        return ResponseEntity.ok(userResponse);
    }
}
