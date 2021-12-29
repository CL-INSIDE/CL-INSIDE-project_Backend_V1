package com.example.sns.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    public Object getPrincipal(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
