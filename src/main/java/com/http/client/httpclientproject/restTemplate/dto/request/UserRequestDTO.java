package com.http.client.httpclientproject.restTemplate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;

@Data
public class UserRequestDTO {
    @Schema(description = "사용자명", example = "admin")
    private String userName;

    @Schema(description = "나이", example = "25")
    private Integer age;
}
