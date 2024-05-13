package com.http.client.httpclientproject.webClient.service;

import com.http.client.httpclientproject.restTemplate.dto.request.RestRequestDTO;
import com.http.client.httpclientproject.restTemplate.dto.response.RestResponseDTO;
import com.http.client.httpclientproject.util.WebClientUtil;
import com.http.client.httpclientproject.webClient.dto.request.RequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientService {

    private final WebClientUtil webClientUtil;
    public RestResponseDTO postData(RestRequestDTO requestDTO){
        log.info("{}", requestDTO.getContent());
        return webClientUtil.post("http://localhost:8080/rest-template/request",
                requestDTO,
                RestResponseDTO.class
                );
    }
}
