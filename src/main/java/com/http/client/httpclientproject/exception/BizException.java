package com.http.client.httpclientproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class BizException extends RuntimeException{
    private final ErrorCode errorCode;
}
