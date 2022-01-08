package com.example.clinside.domain.emotion.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class LikeAlreadyExistsException extends SnsException {
    public static SnsException EXCEPTION =
            new LikeAlreadyExistsException();

    private LikeAlreadyExistsException() {
        super(ErrorCode.LIKE_ALREADY_EXISTS);
    }
}
