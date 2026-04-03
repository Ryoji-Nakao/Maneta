package com.nakao.maneta.controller;

import com.nakao.maneta.dto.request.LoginRequest;
import com.nakao.maneta.dto.request.RegisterRequest;
import com.nakao.maneta.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.status(201).body("登録しました");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        var token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
