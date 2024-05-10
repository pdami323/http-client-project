package com.http.client.httpclientproject.restTemplate.controller;

import com.http.client.httpclientproject.restTemplate.dto.request.RestRequestDTO;
import com.http.client.httpclientproject.restTemplate.dto.response.RestResponseDTO;
import com.http.client.httpclientproject.restTemplate.service.RestTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-template")
@Tag(name = "01. RestTemplate", description = "RestTemplate")
public class RestTemplateController {

    private final RestTemplateService restTemplateService;

    @PostMapping("/request")
    @Operation(summary = "호출 api", description = "호출 api")
    public RestResponseDTO requestApi(@Valid @RequestBody RestRequestDTO restRequestDTO){
        log.info("{}", restRequestDTO);
        return restTemplateService.requestApi(restRequestDTO);
    }

    @GetMapping("/response")
    @Operation(summary = "rest로 호출", description = "rest로 호출")
    @Parameters({
            @Parameter(name = "content", description = "내용", schema = @Schema(type = "string"))
    })
    public ResponseEntity<RestResponseDTO> responseApi(@Parameter(hidden = true) RestRequestDTO restRequestDTO){
        log.info("{}", restRequestDTO);
        RestResponseDTO restResponseDTO = restTemplateService.responseApi(restRequestDTO);
        return new ResponseEntity<>(restResponseDTO, HttpStatus.OK);
    }
}
