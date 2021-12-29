package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class InvalidRoleException extends SnsException {

    public static SnsException EXCEPTION =
            new InvalidRoleException();

    private InvalidRoleException(){
        super(ErrorCode.INVALID_ROLE);
    }
}
