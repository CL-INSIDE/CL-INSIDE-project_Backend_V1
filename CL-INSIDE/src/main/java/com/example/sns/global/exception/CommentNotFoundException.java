package com.example.sns.global.exception;

import com.example.sns.global.error.ErrorCode;
import com.example.sns.global.error.exception.SnsException;

public class CommentNotFoundException extends SnsException {
    public static SnsException EXCEPTION =
            new CommentNotFoundException();

    private CommentNotFoundException(){
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
