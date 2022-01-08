package com.example.clinside.domain.auth.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class ExpiredRefreshTokenException extends SnsException {
    public static SnsException EXCEPTION =
            new ExpiredRefreshTokenException();

    private ExpiredRefreshTokenException() {
        super(ErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}
