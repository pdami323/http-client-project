package com.http.client.httpclientproject.restTemplate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestRequestDTO {
    @Schema(description = "내용")
    private String content;
}
