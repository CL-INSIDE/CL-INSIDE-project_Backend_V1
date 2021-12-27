package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class UserIdAlreadyExistException extends SnsException {
    public static SnsException EXCEPTION =
            new UserIdAlreadyExistException();

    private UserIdAlreadyExistException(){
        super(ErrorCode.USER_ID_ALREADY_EXISTS);
    }
}
