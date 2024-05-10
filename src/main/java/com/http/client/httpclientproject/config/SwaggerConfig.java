package com.http.client.httpclientproject.config;

import com.http.client.httpclientproject.properties.SwaggerProperty;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class SwaggerConfig {

    private SwaggerProperty swaggerProperty;
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(
                new Info()
                        .title(swaggerProperty.getTitle())
                        .version(swaggerProperty.getVersion())
                        .description(swaggerProperty.getDescription())
        ).components(new Components());
    }
}
