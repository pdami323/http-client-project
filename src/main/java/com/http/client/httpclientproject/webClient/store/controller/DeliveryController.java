package com.http.client.httpclientproject.webClient.store.controller;

import com.http.client.httpclientproject.common.config.WebClientConfig;
import com.http.client.httpclientproject.common.exception.UserException;
import com.http.client.httpclientproject.webClient.store.dto.Food;
import com.http.client.httpclientproject.webClient.store.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.http.client.httpclientproject.common.exception.ErrorCode.INVALID_PARAMETER;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deliver")
@Tag(name = "03. WebClient-delivery", description = "앱으로 주문하기")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final WebClientConfig webClientConfig;

    @GetMapping("/welcome")
    @Operation(summary = "주문 환영인사", description = "주문 환영인사")
    public String deliveryWelcome(@RequestParam boolean block, @RequestParam int people){
        long startTime = System.currentTimeMillis();
        welcomeCustomer(block, people);
        long endTime = System.currentTimeMillis();
        String time = (double) (endTime-startTime)/1000 + "초";
        log.info("{}명 입장 : {}", people, time);
        return time;
    }

    @GetMapping("/menu")
    @Operation(summary = "메뉴판", description = "메뉴판")
    public List<Food> getMenu(@RequestParam String name){
        Flux<Food> flux = webClientConfig.webClient().method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path("/store/menu").queryParam("name", name).build())
                .retrieve()
                .bodyToFlux(Food.class);
        return flux.toStream().collect(Collectors.toList());
    }

    private void welcomeCustomer(boolean block, int people){
        while (people-- > 0){
            log.info("{}번째 손님", people);
            Mono<String> mono = webClientConfig.webClient().method(HttpMethod.GET)
                    .uri("/store/welcome")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        log.error("[DeliveryController.welcomeCustomer] 에러 발생");
                        throw new UserException(INVALID_PARAMETER);
                    })
                    .bodyToMono(String.class);
            if(block){ //블럭
                log.info("mono.block() : {}", mono.block());
            }else{  //논블럭
                log.info("mono.subscribe()");
                mono.subscribe();
            }
        }
    }
}
