package com.example.clinside.domain.emotion.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class HateAlreadyExistsException extends SnsException {
    public static SnsException EXCEPTION =
            new HateAlreadyExistsException();

    private HateAlreadyExistsException() {
        super(ErrorCode.HATE_ALREADY_EXISTS);
    }

}
