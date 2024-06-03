package com.http.client.httpclientproject.webClientExample.controller;

import com.http.client.httpclientproject.example.service.webClient.WebCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@Slf4j
@SpringBootTest
class RestWebCustomerControllerTest {

    @Autowired
    private WebCustomerService webCustomerService;
    @Test
    void customer(){
        webCustomerService.asyncTest();
    }

    @Test
    void test(){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int a;
        for (int i=0;i<10;i++){
            a = random.nextInt(10);
            log.info("{}", a);
        }
    }

}