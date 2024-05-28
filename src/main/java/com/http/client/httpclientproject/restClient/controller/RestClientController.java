package com.http.client.httpclientproject.restClient.controller;

import com.http.client.httpclientproject.common.config.RestClientConfig;
import com.http.client.httpclientproject.restTemplate.dto.request.RestRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rest-client")
@RequiredArgsConstructor
@Tag(name = "05. RestClient", description = "RestClient")
public class RestClientController {

    private final RestClientConfig restClientConfig;

    @PostMapping
    public ResponseEntity<String> rest(@RequestBody RestRequestDTO restRequestDTO){
        log.info("{}", restRequestDTO.getContent());
        ResponseEntity<String> result = restClientConfig.restClient().post()
                .uri("http://localhost:8080/rest-template/request")
                .body(restRequestDTO)
                .retrieve()
                .toEntity(String.class);
        return result;
    }

}
