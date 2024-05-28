package com.http.client.httpclientproject.example.service;

import com.http.client.httpclientproject.common.config.WebClientConfig;
import com.http.client.httpclientproject.common.exception.BizException;
import com.http.client.httpclientproject.common.exception.ErrorCode;
import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final WebClientConfig webClientConfig;
    private static final String[] MENU = {
            "AMERICANO", "LATTE", "BREAD", "ICE CREAM"
    };

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
                        throw new BizException(ErrorCode.INTERNAL_SERVER_ERROR);
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

    @Transactional
    public void asyncTestSeparated() {
        log.info("[CustomerService.asyncTestSeparated] start");
        log.info("[CustomerService.asyncTestSeparated] 1. 주문");
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
                    .uri(uriBuilder -> uriBuilder.path("/order").build())
                    .bodyValue(createOrderRequestDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        throw new BizException(ErrorCode.INTERNAL_SERVER_ERROR);
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
        log.info("[CustomerService.asyncTestSeparated] 2. 음식 제공");
        log.info("[CustomerService.asyncTestSeparated] end");
    }
}
