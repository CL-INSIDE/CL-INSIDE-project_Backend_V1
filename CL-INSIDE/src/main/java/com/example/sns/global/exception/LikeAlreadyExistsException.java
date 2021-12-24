package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class LikeAlreadyExistsException extends SnsException {
    public static SnsException EXCEPTION =
            new LikeAlreadyExistsException();

    private LikeAlreadyExistsException(){
        super(ErrorCode.LIKE_ALREADY_EXISTS);
    }
}
