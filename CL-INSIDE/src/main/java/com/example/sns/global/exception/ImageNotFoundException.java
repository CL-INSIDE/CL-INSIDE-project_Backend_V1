package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class ImageNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new ImageNotFoundException();

    private ImageNotFoundException(){
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
