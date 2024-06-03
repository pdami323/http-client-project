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
@Tag(name = "07. WebClient 예제", description = "WebClient 예제")
public class WebCustomerController {

    private final WebCustomerService webCustomerService;

    @GetMapping("/sync-blocking")
    @Operation(summary = "Sync-Blocking 처리", description = "Sync-Blocking 처리")
    public ResponseEntity<Void> syncTest(){
        webCustomerService.syncBlocking();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/async-nonBlocking")
    @Operation(summary = "Async-NonBlocking 처리", description = "Async-NonBlocking 처리")
    public ResponseEntity<Void> asyncNonBlocking(){
        webCustomerService.asyncNonBlocking();
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/async-nonBlocking")
//    @Operation(description = "비동기 처리", summary = "비동기 처리")
//    public ResponseEntity<Void> asyncTest(){
//        webCustomerService.asyncNonBlocking();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @GetMapping("/async-blocking")
//    @Operation(summary = "비동기-블로킹 처리", description = "비동기-블로킹 처리")
//    public ResponseEntity<Void> asyncBlocking(){
//        webCustomerService.asyncBlocking();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }






}
