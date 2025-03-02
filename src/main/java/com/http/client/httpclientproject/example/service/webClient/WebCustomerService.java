package com.http.client.httpclientproject.example.service.webClient;

import com.http.client.httpclientproject.common.config.WebClientConfig;
import com.http.client.httpclientproject.common.exception.UserException;
import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static com.http.client.httpclientproject.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebCustomerService {

    private final WebClientConfig webClientConfig;
    private final Random random = new Random();
    private static final String[] MENU = {
            "AMERICANO", "LATTE", "BREAD", "ICE CREAM", "MILK"
    };
    private static final Integer COUNT = 3;

    @Transactional
    public void syncBlocking() {
        log.info("[CustomerService.syncBlocking] sync-Blocking TEST start");
        int order_num;
        int count;
        for (int i=1; i<=COUNT;i++){
            log.info("[CustomerService.syncBlocking] {}번째 손님 입장", i);
            order_num = random.nextInt(MENU.length);
            count = random.nextInt(10)+1;
            CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
            createOrderRequestDTO.setCustomerId(i);
            createOrderRequestDTO.setMenu(MENU[order_num]);
            createOrderRequestDTO.setQuantity(count);
            GetOrderResponseDTO result = webClientConfig.webClient().post()
                    .uri(uriBuilder -> uriBuilder.path("/order/total").build())
                    .bodyValue(createOrderRequestDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        throw new UserException(INTERNAL_SERVER_ERROR);
                    })
                    .bodyToMono(GetOrderResponseDTO.class)
                    .block();
            if(result != null){
                resultCheck(result);
            }
        }
        log.info("[CustomerService.syncBlocking] sync-Blocking TEST end");
    }

    @Transactional
    public void asyncNonBlocking() {
        log.info("[CustomerService.syncNonBlocking] async-NonBlocking TEST start");
        int order_num;
        int count;
        for (int i=1;i<=COUNT;i++){
            log.info("[CustomerService.syncNonBlocking] {}번째 손님 입장", i);
            order_num = random.nextInt(MENU.length);
            count = random.nextInt(10)+1;
            CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
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
                        resultCheck(getOrderResponseDTO);
                    }
            );
        }
        log.info("[CustomerService.syncNonBlocking] async-NonBlocking TEST end");
    }

//    @Transactional
//    public void asyncNonBlocking() {
//        log.info("[CustomerService.asyncNonBlocking] 비동기 테스트 start");
//        int order_num;
//        int count;
//        for (int i=1;i<=COUNT;i++){
//            log.info("[CustomerService.asyncNonBlocking] {}번째 손님 입장", i);
//            order_num = random.nextInt(MENU.length);
//            count = random.nextInt(10)+1;
//            CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
//            createOrderRequestDTO.setCustomerId(i);
//            createOrderRequestDTO.setMenu(MENU[order_num]);
//            createOrderRequestDTO.setQuantity(count);
////            Mono<GetOrderResponseDTO> result = webClientConfig.webClient().post()
////                    .uri(uriBuilder -> uriBuilder.path("/order/total").build())
////                    .bodyValue(createOrderRequestDTO)
////                    .retrieve()
////                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
////                        throw new UserException(INTERNAL_SERVER_ERROR);
////                    })
////                    .bodyToMono(GetOrderResponseDTO.class);
//            CompletableFuture<Mono<GetOrderResponseDTO>> future = CompletableFuture.supplyAsync(()->
//                            webClientConfig.webClient().post()
//                                    .uri(uriBuilder -> uriBuilder.path("/order/total").build())
//                                    .bodyValue(createOrderRequestDTO)
//                                    .retrieve()
//                                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
//                                        throw new UserException(INTERNAL_SERVER_ERROR);
//                                    })
//                                    .bodyToMono(GetOrderResponseDTO.class)
//                    ).thenApply(s -> {
//                        return s.subscribe()
//            });
//            log.info("성공");
//            future.join().subscribe(getOrderResponseDTO -> {
//                log.info("성공");
//                resultCheck(getOrderResponseDTO);
//            });
////            result.subscribe(
////                    getOrderResponseDTO -> {
////                        log.info("성공");
////                        resultCheck(getOrderResponseDTO);
////                    }
////            );
//        }
//        log.info("[CustomerService.asyncNonBlocking] 비동기 테스트 end");
//    }



//    public void asyncBlocking() {
//        log.info("[CustomerService.asyncBlocking] async-Blocking 테스트 start");
//        int order_num;
//        int count;
//        for (int i=1; i<=COUNT;i++){
//            log.info("[CustomerService.asyncBlocking] {}번째 손님 입장", i);
//            order_num = random.nextInt(MENU.length);
//            count = random.nextInt(10)+1;
//            CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
//            createOrderRequestDTO.setCustomerId(i);
//            createOrderRequestDTO.setMenu(MENU[order_num]);
//            createOrderRequestDTO.setQuantity(count);
//            CompletableFuture<GetOrderResponseDTO> result = CompletableFuture.supplyAsync(()->
//                    webClientConfig.webClient().post()
//                            .uri(uriBuilder -> uriBuilder.path("/order/total").build())
//                            .bodyValue(createOrderRequestDTO)
//                            .retrieve()
//                            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
//                                throw new UserException(INTERNAL_SERVER_ERROR);
//                            })
//                            .bodyToMono(GetOrderResponseDTO.class).block()
//            );
//            try {
//                resultCheck(result.join());
//            }catch (Exception e){
//                log.error("[CustomerService.asyncBlocking] 에러 발생");
//            }
//        }
//        log.info("[CustomerService.asyncBlocking] async-Blocking 테스트 end");
//    }


    @Transactional
    public void asyncTestSeparated() {
        log.info("[CustomerService.asyncTestSeparated] start");
        log.info("[CustomerService.asyncTestSeparated] 1. 주문");
        Random random = new Random();
        int order_num;
        int count;
        for (int i=1;i<=10;i++){
            log.info("[CustomerService.asyncTestSeparated] {}번째 손님 입장", i);
            order_num = random.nextInt(MENU.length);
            count = random.nextInt(10)+1;
            log.info("{}번째 메뉴로 {}개", order_num, count);
            CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
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

    private void resultCheck(GetOrderResponseDTO result){
        log.info("[CustomerService.resultCheck] {}번째 손님 퇴장", result.getCustomerId());
    }
}
