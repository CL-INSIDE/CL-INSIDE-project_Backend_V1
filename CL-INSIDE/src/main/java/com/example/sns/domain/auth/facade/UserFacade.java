package com.example.sns.domain.auth.facade;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.exception.UserNotFoundException;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.global.exception.AuthenticationNotFoundException;
import com.example.sns.global.security.auth.UserDetails;
import com.example.sns.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserUtil util;

    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getPrincipal() == null
                || !(authentication.getPrincipal() instanceof User))
            return null;

        return (User) authentication.getPrincipal();
    }

    public User getUserId(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    public User getCurrentUser(){
        Object principal = util.getPrincipal();

        if (!(principal instanceof UserDetails))
            throw AuthenticationNotFoundException.EXCEPTION;

        return ((UserDetails) principal).getUser();
    }

}
