package com.http.client.httpclientproject.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final RestTemplateConfig restTemplateConfig;

    @Bean
    public RestClient restClient(){
//        return RestClient.create(restTemplateConfig.restTemplate());
        return RestClient.builder()
                .baseUrl("http://localhost:8080/")
                .build();
    }
}
