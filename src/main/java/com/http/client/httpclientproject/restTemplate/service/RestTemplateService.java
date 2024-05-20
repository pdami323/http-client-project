package com.http.client.httpclientproject.restTemplate.service;

import com.http.client.httpclientproject.common.config.RestTemplateConfig;
import com.http.client.httpclientproject.restTemplate.dao.RestTemplateDAO;
import com.http.client.httpclientproject.restTemplate.dto.request.RestRequestDTO;
import com.http.client.httpclientproject.restTemplate.dto.request.UserRequestDTO;
import com.http.client.httpclientproject.restTemplate.dto.response.RestResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestTemplateService {

    private final RestTemplateConfig restTemplateConfig;

    private final RestTemplateDAO restTemplateDAO;

    public RestResponseDTO requestApi(RestRequestDTO restRequestDTO) {
        RestResponseDTO restResponseDTO = new RestResponseDTO();
        restResponseDTO.setMethod("GET");
        restResponseDTO.setContent(restRequestDTO.getContent());
        return restResponseDTO;
    }

    public RestResponseDTO responseApi(RestRequestDTO restRequestDTO) {
        log.info("[RestTemplateService.responseApi] {}", restRequestDTO.getContent());
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080")
                .path("/rest-template/request")
                .encode().build().toUri();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<RestRequestDTO> httpEntity = new HttpEntity<>(restRequestDTO,headers);

        ResponseEntity<RestResponseDTO> result = restTemplateConfig.restTemplate()
                                    .exchange(uri, HttpMethod.POST,httpEntity, RestResponseDTO.class);
        return result.getBody();
    }

    @Transactional
    public void createUser(UserRequestDTO userRequestDTO) {
        log.info("[RestTemplateService.createUser] start");
        restTemplateDAO.createUser(userRequestDTO);
        log.info("[RestTemplateService.createUser] end");
    }
}
