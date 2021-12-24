package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class LikeNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new LikeNotFoundException();

    private LikeNotFoundException(){
        super(ErrorCode.LIKE_NOT_FOUND);
    }
}
