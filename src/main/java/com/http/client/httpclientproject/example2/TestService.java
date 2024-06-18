package com.http.client.httpclientproject.example2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class TestService {

    public String sync1() throws InterruptedException {
        Thread.sleep(3000);
        log.info("sync1");
        return "sync1";
    }

    public String sync2() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("sync2");
        return "sync2";
    }

    public String sync3() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("sync3");
        return "sync3";
    }

    @Async
    public String async1() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("async1");
        return "async1";
    }

    @Async
    public CompletableFuture<String> async2() throws InterruptedException, ExecutionException {
        ExecutorService executor
                = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            Thread.sleep(3000);
            System.out.println("async2");
            return "async2";
        });
        return CompletableFuture.completedFuture(future.get());
    }

    @Async
    public String async3() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("async3");
        return "async3";
    }
}
