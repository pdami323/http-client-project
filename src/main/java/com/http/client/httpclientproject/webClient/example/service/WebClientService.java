package com.http.client.httpclientproject.webClient.example.service;

import com.http.client.httpclientproject.restTemplate.dto.request.RestRequestDTO;
import com.http.client.httpclientproject.restTemplate.dto.response.RestResponseDTO;
import com.http.client.httpclientproject.common.util.WebClientUtil;
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
        return webClientUtil.post("/rest-template/request",
                requestDTO,
                RestResponseDTO.class
                );
    }
}
