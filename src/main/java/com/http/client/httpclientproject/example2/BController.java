package com.http.client.httpclientproject.example2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BController {

    private final TestService testService;
    private int i = 5;
    private static int FOO = 2;
    @GetMapping("/sync")
    public ResponseEntity<Object> sync() throws InterruptedException {
        String result1 = testService.sync1();
        String result2 = testService.sync2();
        String result3 = testService.sync3();
        String answer = String.join(",", result1, result2, result3);
        log.info("sync called");
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @GetMapping("/async")
    public ResponseEntity<Object> async() throws InterruptedException, ExecutionException {
        String result1 = testService.async1();
        String result2 = testService.async2().get();
        String result3 = testService.async3();
        String answer = String.join(",", result1, result2, result3);
        System.out.println("async called"); //NOPMD - suppressed SystemPrintln - TODO explain reason for suppression
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
}
