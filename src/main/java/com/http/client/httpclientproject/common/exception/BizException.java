package com.http.client.httpclientproject.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BizException extends RuntimeException{
    private final ErrorCode errorCode;
}
