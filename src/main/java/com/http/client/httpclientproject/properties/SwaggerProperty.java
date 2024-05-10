package com.http.client.httpclientproject.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "springdoc")
public class SwaggerProperty {
    private String title;
    private String version;
    private String description;

}
