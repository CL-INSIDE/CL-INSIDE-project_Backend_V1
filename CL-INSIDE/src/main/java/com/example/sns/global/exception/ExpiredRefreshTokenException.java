package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class ExpiredRefreshTokenException extends SnsException {
    public static SnsException EXCEPTION =
            new ExpiredRefreshTokenException();

    private ExpiredRefreshTokenException(){
        super(ErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}
