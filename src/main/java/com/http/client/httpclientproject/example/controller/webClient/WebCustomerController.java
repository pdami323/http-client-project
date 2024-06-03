package com.http.client.httpclientproject.example.controller.webClient;

import com.http.client.httpclientproject.example.service.webClient.WebCustomerService;
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
@RequestMapping("/customer")
@Tag(name = "06. WebClient 예제", description = "WebClient 예제")
public class WebCustomerController {

    private final WebCustomerService webCustomerService;

    @GetMapping("/async")
    @Operation(description = "비동기 처리", summary = "비동기 처리")
    public ResponseEntity<Void> asyncTest(){
        webCustomerService.asyncTest();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sync")
    @Operation(summary = "동기 처리", description = "동기 처리")
    public ResponseEntity<Void> syncTest(){
        webCustomerService.syncTest();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
