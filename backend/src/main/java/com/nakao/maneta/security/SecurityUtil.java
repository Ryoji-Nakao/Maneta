package com.nakao.maneta.security;

import com.nakao.maneta.entity.User;
import com.nakao.maneta.exception.ResourceNotFoundException;
import com.nakao.maneta.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private final UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("ユーザーが見つかりません"));
    }
}
