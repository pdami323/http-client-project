package com.http.client.httpclientproject.util;

import com.http.client.httpclientproject.config.WebClientConfig;
import com.http.client.httpclientproject.exception.BizException;
import com.http.client.httpclientproject.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> T get(String url, Class<T> resposneDTOClass){
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BizException(new ErrorCode(HttpStatus.BAD_REQUEST, "fail_web_client_get"))))
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
