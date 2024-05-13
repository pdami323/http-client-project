package com.http.client.httpclientproject.webClient.controller;

import com.http.client.httpclientproject.restTemplate.dto.request.RestRequestDTO;
import com.http.client.httpclientproject.restTemplate.dto.response.RestResponseDTO;
import com.http.client.httpclientproject.webClient.dto.request.RequestDTO;
import com.http.client.httpclientproject.webClient.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class WebClientController {

    private final WebClientService webClientService;

    @PostMapping("/web-client")
    public RestResponseDTO postData(@RequestBody RestRequestDTO requestDTO){
        RestResponseDTO result = webClientService.postData(requestDTO);
        return result;
    }
}
