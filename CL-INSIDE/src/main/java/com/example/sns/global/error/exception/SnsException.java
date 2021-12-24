package com.example.sns.global.error.exception;

import com.example.sns.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SnsException extends RuntimeException{
    private final ErrorCode errorCode;
}
