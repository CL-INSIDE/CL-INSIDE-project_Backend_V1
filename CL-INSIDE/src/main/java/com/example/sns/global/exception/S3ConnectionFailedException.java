package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class S3ConnectionFailedException extends SnsException {
    public static SnsException EXCEPTION =
            new S3ConnectionFailedException();

    private S3ConnectionFailedException(){
        super(ErrorCode.S3_CONNECTION_FAILED);
    }
}
