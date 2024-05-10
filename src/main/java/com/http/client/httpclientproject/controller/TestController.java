package com.http.client.httpclientproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "01. 테스트", description = "테스트")
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "테스트 조회", description = "테스트")
    @GetMapping
    public ResponseEntity<String> get(){
        return new ResponseEntity<>("aa", HttpStatus.OK);
    }
}
