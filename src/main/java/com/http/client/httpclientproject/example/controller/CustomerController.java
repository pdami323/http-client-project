package com.http.client.httpclientproject.example.controller;

import com.http.client.httpclientproject.example.service.CustomerService;
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
@Tag(name = "06. 입장", description = "입장")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    @Operation(description = "주문과 음식 동시", summary = "주문 후 음식 받기")
    public ResponseEntity<Void> asyncTest(){
        customerService.asyncTest();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @Operation(description = "주문과 음식 개별", summary = "주문 후 음식 받기")
    public ResponseEntity<Void> asyncTest2(){
        customerService.asyncTestSeparated();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
