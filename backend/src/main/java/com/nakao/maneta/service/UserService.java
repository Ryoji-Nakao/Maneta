package com.nakao.maneta.service;

import com.nakao.maneta.dto.response.UserResponse;
import com.nakao.maneta.entity.User;
import com.nakao.maneta.exception.ResourceNotFoundException;
import com.nakao.maneta.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse selectUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("ユーザーが見つかりません"));
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(username);
        userResponse.setEmail(user.getEmail());
        userResponse.setBio(user.getBio());
        userResponse.setCreatedAt(user.getCreatedAt());
        return userResponse;
    }
}
