package com.example.clinside.domain.emotion.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class HateNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new HateNotFoundException();

    private HateNotFoundException() {
        super(ErrorCode.HATE_NOT_FOUND);
    }
}
