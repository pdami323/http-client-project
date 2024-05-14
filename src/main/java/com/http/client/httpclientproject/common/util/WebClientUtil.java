package com.http.client.httpclientproject.common.util;

import com.http.client.httpclientproject.common.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> T get(String url, Class<T> resposneDTOClass){
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .bodyToMono(resposneDTOClass)
                .block();
    }

    public <T> T post(String url, Object requestDto, Class<T> resposneDTOClass){
        return webClientConfig.webClient().method(HttpMethod.POST)
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(resposneDTOClass)
                .block();
    }
}
