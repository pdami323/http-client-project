package com.http.client.httpclientproject.example.service;

import com.http.client.httpclientproject.common.config.WebClientConfig;
import com.http.client.httpclientproject.common.exception.UserException;
import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

import static com.http.client.httpclientproject.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final WebClientConfig webClientConfig;
    private static final String[] MENU = {
            "AMERICANO", "LATTE", "BREAD", "ICE CREAM"
    };
    private final WebClient webClient;

    public void asyncTest() {
        log.info("[CustomerService.async] 비동기 테스트 start");
        CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
        Random random = new Random();
        int order_num;
        int count;
        for (int i=1;i<=10;i++){
            log.info("[CustomerService.async] {}번째 손님 입장", i);
            order_num = random.nextInt(MENU.length);
            count = random.nextInt(10)+1;
            log.info("{}번째 메뉴로 {}개", order_num, count);
            createOrderRequestDTO.setCustomerId(i);
            createOrderRequestDTO.setMenu(MENU[order_num]);
            createOrderRequestDTO.setQuantity(count);
            Mono<GetOrderResponseDTO> result = webClientConfig.webClient().post()
                    .uri(uriBuilder -> uriBuilder.path("/order/total").build())
                    .bodyValue(createOrderRequestDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        throw new UserException(INTERNAL_SERVER_ERROR);
                    })
                    .bodyToMono(GetOrderResponseDTO.class);
            result.subscribe(
                    getOrderResponseDTO -> {
                        log.info("성공");
                        log.info("{}", getOrderResponseDTO);
                    },
                    error -> {
                        log.info("에러");
                    }
            );
            log.info("[CustomerService.async] {}번째 손님 퇴장", i);
        }
        log.info("[CustomerService.async] 비동기 테스트 end");
    }

    public void asyncTestSeparated() {
        log.info("[CustomerService.asyncTestSeparated] start");
        log.info("[CustomerService.asyncTestSeparated] 1. 주문");
        CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
        Random random = new Random();
        int order_num;
        int count;
        for (int i=1;i<=10;i++){
            log.info("[CustomerService.asyncTestSeparated] {}번째 손님 입장", i);
            order_num = random.nextInt(MENU.length);
            count = random.nextInt(10)+1;
            log.info("{}번째 메뉴로 {}개", order_num, count);
            createOrderRequestDTO.setCustomerId(i);
            createOrderRequestDTO.setMenu(MENU[order_num]);
            createOrderRequestDTO.setQuantity(count);
            Mono<GetOrderResponseDTO> result = webClientConfig.webClient().post()
                    .uri(uriBuilder -> uriBuilder.path("/order").build())
                    .bodyValue(createOrderRequestDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        throw new UserException(INTERNAL_SERVER_ERROR);
                    })
                    .bodyToMono(GetOrderResponseDTO.class);
            result.subscribe(
                    getOrderResponseDTO -> {
                        log.info("성공");
                        log.info("{}", getOrderResponseDTO);
                    },
                    error -> {
                        log.info("에러");
                    }
            );
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                log.error("[CustomerService.asyncTestSeparated] 손님이 없는 중 오류가 발생했습니다.");
            }
        }
        log.info("[CustomerService.asyncTestSeparated] 2. 음식 제공");
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                log.error("[CustomerService.asyncTestSeparated] 쉬는 중 오류가 발생했습니다.");
            }
            Flux<GetOrderResponseDTO> result = webClientConfig.webClient().get()
                    .uri(uriBuilder -> uriBuilder.path("/order").build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        throw new UserException(INTERNAL_SERVER_ERROR);
                    }).bodyToFlux(GetOrderResponseDTO.class);
            result.subscribe();
            List<GetOrderResponseDTO> orderList = result.toStream().toList();
            log.info("[CustomerService.asyncTestSeparated] {}번째 손님 퇴장", orderList);
        }
        log.info("[CustomerService.asyncTestSeparated] end");
    }
}
