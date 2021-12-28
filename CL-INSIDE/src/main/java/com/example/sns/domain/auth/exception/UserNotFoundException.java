package com.example.sns.domain.auth.domain.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class UserNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new UserNotFoundException();

    private UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
