package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class AuthenticationNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new AuthenticationNotFoundException();

    private AuthenticationNotFoundException(){
        super(ErrorCode.AUTHENTICATION_NOT_FOUND);
    }
}
