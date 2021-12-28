package com.example.sns.domain.auth.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class ExpiredAccessTokenException extends SnsException {
    public static SnsException EXCEPTION =
            new ExpiredAccessTokenException();

    private ExpiredAccessTokenException(){
        super(ErrorCode.EXPIRED_ACCESS_TOKEN);
    }
}
