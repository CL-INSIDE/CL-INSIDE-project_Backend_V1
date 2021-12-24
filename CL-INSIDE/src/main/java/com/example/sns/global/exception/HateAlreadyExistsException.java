package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class HateAlreadyExistsException extends SnsException {
    public static SnsException EXCEPTION =
            new HateAlreadyExistsException();

    private HateAlreadyExistsException(){
        super(ErrorCode.HATE_ALREADY_EXISTS);
    }

}
