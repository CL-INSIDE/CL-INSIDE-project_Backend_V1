package com.example.sns.global.exception;

import com.example.sns.global.error.exception.SnsException;

public class InvalidPasswordException extends SnsException {
    public static SnsException EXCEPTION =
            new InvalidPasswordException();

    private InvalidPasswordException(){
        super();
    }
}
