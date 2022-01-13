package com.example.clinside.domain.auth.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class AuthenticationNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new AuthenticationNotFoundException();

    private AuthenticationNotFoundException() {
        super(ErrorCode.AUTHENTICATION_NOT_FOUND);
    }
}
