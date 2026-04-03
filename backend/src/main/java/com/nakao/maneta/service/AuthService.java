package com.nakao.maneta.service;


import com.nakao.maneta.dto.request.LoginRequest;
import com.nakao.maneta.dto.request.RegisterRequest;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.repository.UserRepository;
import com.nakao.maneta.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request){
        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public String login(LoginRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPasswordHash()))throw new RuntimeException("パスワードが違います");
        return jwtUtil.generateToken(request.getUsername());
    }
}
