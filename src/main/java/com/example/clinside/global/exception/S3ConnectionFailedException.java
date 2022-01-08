package com.example.clinside.global.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class S3ConnectionFailedException extends SnsException {
    public static SnsException EXCEPTION =
            new S3ConnectionFailedException();

    private S3ConnectionFailedException() {
        super(ErrorCode.S3_CONNECTION_FAILED);
    }
}
