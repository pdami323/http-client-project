package com.http.client.httpclientproject.restTemplate.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestResponseDTO {
    private String method;
    private String content;
}
