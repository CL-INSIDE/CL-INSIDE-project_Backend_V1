package com.example.clinside.domain.emotion.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class LikeNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new LikeNotFoundException();

    private LikeNotFoundException() {
        super(ErrorCode.LIKE_NOT_FOUND);
    }
}
