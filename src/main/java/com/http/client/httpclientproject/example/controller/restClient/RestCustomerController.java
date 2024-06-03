package com.http.client.httpclientproject.example.controller.restClient;

import com.http.client.httpclientproject.example.service.restClient.RestCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/customer")
@Tag(name = "08. RestClient 예제", description = "RestClient 예제")
public class RestCustomerController {

    private final RestCustomerService restCustomerService;

    @GetMapping("/sync")
    @Operation(summary = "동기", description = "동기")
    public ResponseEntity<Void> syncTest(){
        restCustomerService.syncTest();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/async")
    @Operation(summary = "비동기", description = "비동기")
    public ResponseEntity<Void> asyncTest(){
        restCustomerService.asyncTest();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
