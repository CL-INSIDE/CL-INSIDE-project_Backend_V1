package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class PostNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new PostNotFoundException();

    private PostNotFoundException(){
        super(ErrorCode.POST_NOT_FOUND);
    }
}
