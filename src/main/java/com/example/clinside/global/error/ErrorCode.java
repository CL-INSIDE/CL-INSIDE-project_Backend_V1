package com.example.clinside.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INCORRECT_TOKEN(400, "Incorrect Token"),
    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_ACCESS_TOKEN(401, "Expired Access Token"),
    EXPIRED_REFRESH_TOKEN(401, "Expired Refresh Token"),
    REFRESH_TOKEN_NOT_FOUND(404, "Refresh Token Not Found"),

    USER_ID_ALREADY_EXISTS(409, "User Id Already Exists"),
    USER_NAME_ALREADY_EXISTS(409, "User Name Already Exists"),
    USER_NOT_FOUND(404, "User Not Found"),
    USER_USERNAME_ALREADY_EXISTS(409, "User Already Exists"),
    INVALID_PASSWORD(401, "Invalid Password"),

    AUTHENTICATION_NOT_FOUND(403, "Authentication Not Found"),
    POST_NOT_FOUND(404, "Post Not Found"),

    LIKE_ALREADY_EXISTS(409, "Like Already Exists"),
    LIKE_NOT_FOUND(404, "Like Not Found"),

    S3_CONNECTION_FAILED(500, "S3 Connection Failed"),

    IMAGE_NOT_FOUND(404, "Image Not Found"),

    HATE_ALREADY_EXISTS(409, "Hate Already Exists"),
    HATE_NOT_FOUND(404, "Hate Not Found"),

    INVALID_ROLE(401, "Invalid Role"),

    COMMENT_NOT_FOUND(404, "Comment Not Found");

    private final int status;
    private final String message;
}
