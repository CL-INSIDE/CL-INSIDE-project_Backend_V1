package com.example.clinside.domain.post.exception;

import com.example.clinside.global.error.ErrorCode;
import com.example.clinside.global.error.exception.SnsException;

public class PostNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new PostNotFoundException();

    private PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
