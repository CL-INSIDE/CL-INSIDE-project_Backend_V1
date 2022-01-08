package com.example.clinside.domain.auth.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class UserIdAlreadyExistException extends SnsException {
    public static SnsException EXCEPTION =
            new UserIdAlreadyExistException();

    private UserIdAlreadyExistException() {
        super(ErrorCode.USER_ID_ALREADY_EXISTS);
    }
}
