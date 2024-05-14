package com.http.client.httpclientproject.webClient.store.controller;

import com.http.client.httpclientproject.webClient.store.dto.Food;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Tag(name = "04. WebClient-store", description = "가게에서 주문하기")
public class StoreController {

    private final List<Food> foodList = List.of(
            new Food("카레", 1000, 7000),
            new Food("라면", 500, 5000),
            new Food("새우튀김", 2000, 12000),
            new Food("새우볶음밥", 1000, 10000)
    );

    @GetMapping("/welcome")
    public String welcome() throws InterruptedException {
        Thread.sleep(100);  //입장 후 0.1초 후에 인사
        return "어서오십시오";
    }

    @GetMapping("/menu")
    public List<Food> getFood(@RequestParam String name){
        log.info("[StoreController.getFood] name : {}", name);
        List<Food> list = foodList.stream()
                .filter(food -> food.getFoodName().contains(name))
                .collect(Collectors.toList());
        log.info("end : {}", list);
        return list;
    }
}
