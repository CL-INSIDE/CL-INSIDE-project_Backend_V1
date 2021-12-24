package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class IncorrectTokenException extends SnsException {
    public static SnsException EXCEPTION =
            new IncorrectTokenException();

    private IncorrectTokenException(){
        super(ErrorCode.INCORRECT_TOKEN);
    }
}
