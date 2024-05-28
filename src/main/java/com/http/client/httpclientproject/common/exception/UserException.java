package com.http.client.httpclientproject.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException{
    private final ErrorCode errorCode;
}
