package com.http.client.httpclientproject.common.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    @Bean
    public RestTemplate restTemplate(){
        //Connection Pool 설정 : 설정하지 않으면 Connection Pool을 활용하지 않은 객체가 된다.
        //Spring Version 2.XX
//        HttpClient httpClient = HttpClientBuilder.create()
//                .setMaxConnTotal(5)         //오픈되는 커넥션의 최대값
//                .setMaxConnPerRoute(100)    //IP, 포트 1쌍에 대해 수행할 커넥션 수
//                .build()
//                ;

        //Spring Version 3.XX
        HttpClient httpClient = HttpClientBuilder.create()
                        .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                                .setMaxConnPerRoute(100)
                                .setMaxConnTotal(300)
                                .build())
                        .build();
        factory.setConnectionRequestTimeout(5000);
        factory.setConnectTimeout(3000);    //연결시간초과
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
}
