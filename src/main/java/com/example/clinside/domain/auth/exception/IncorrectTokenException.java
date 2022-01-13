package com.example.clinside.domain.auth.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class IncorrectTokenException extends SnsException {
    public static SnsException EXCEPTION =
            new IncorrectTokenException();

    private IncorrectTokenException() {
        super(ErrorCode.INCORRECT_TOKEN);
    }
}
