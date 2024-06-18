package com.http.client.httpclientproject.example2;

import com.http.client.httpclientproject.common.config.WebClientConfig;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "예제")
public class AController {

    private final WebClientConfig webClientConfig;

    @GetMapping("/block-sync")
    public ResponseEntity<Object> block_sync(){
        Mono<String> mono = webClientConfig.webClient().method(HttpMethod.GET)
                .uri("/sync")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(System.out::println);
        Object result = mono.block();
        log.info("body-sync End");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/block-async")
    public ResponseEntity<Object> block_async(){
        Mono<String> mono = webClientConfig.webClient().method(HttpMethod.GET)
                .uri("/async")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(System.out::println);
//        CompletableFuture<String> future = mono.toFuture();
        Object result = mono.block();
        log.info("block-async End");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/nonblock-sync")
    public ResponseEntity<Object> nonblock_sync() throws ExecutionException, InterruptedException {
        Mono<String> mono = webClientConfig.webClient()
                .method(HttpMethod.GET)
                .uri("/sync")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(System.out::println);
//        CompletableFuture<String> future = mono.toFuture();
        Object result = mono.subscribe();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/nonblock-async")
    public ResponseEntity<Object> nonblock_async(){
        Mono<String> mono = webClientConfig.webClient()
                .method(HttpMethod.GET)
                .uri("/async")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(System.out::println);
        Object result = mono.subscribe();
        log.info("nonblock-async End");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
