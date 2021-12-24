package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class HateNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new HateNotFoundException();

    private HateNotFoundException(){
        super(ErrorCode.HATE_NOT_FOUND);
    }
}
