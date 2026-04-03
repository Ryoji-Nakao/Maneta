package com.nakao.maneta.security;

import com.nakao.maneta.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません"));
        return User.withUsername(user.getUsername()).password(user.getPasswordHash()).roles("User").build();
    }
}
