package com.example.clinside.domain.auth.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class ExpiredAccessTokenException extends SnsException {
    public static SnsException EXCEPTION =
            new ExpiredAccessTokenException();

    private ExpiredAccessTokenException() {
        super(ErrorCode.EXPIRED_ACCESS_TOKEN);
    }
}
