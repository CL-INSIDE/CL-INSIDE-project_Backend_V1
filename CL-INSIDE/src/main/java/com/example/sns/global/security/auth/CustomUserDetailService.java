package com.example.sns.global.security.auth;

import com.example.sns.domain.auth.entity.user.UserRepository;
import com.example.sns.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{

        return userRepository.findById(name)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }
}
