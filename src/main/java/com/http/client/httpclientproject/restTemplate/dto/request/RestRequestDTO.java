package com.http.client.httpclientproject.restTemplate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class RestRequestDTO {
    @NotNull
    @Schema(description = "내용")
    private String content;
}
