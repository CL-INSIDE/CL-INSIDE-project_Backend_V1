package com.example.sns.global.security.auth;

import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{

        return new com.example.sns.global.security.auth.UserDetails(
                userRepository.findByEmail(name)
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION));
    }
}
