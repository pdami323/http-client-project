package com.http.client.httpclientproject.example.service.restClient;

import com.http.client.httpclientproject.common.config.RestClientConfig;
import com.http.client.httpclientproject.common.exception.UserException;
import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.http.client.httpclientproject.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestCustomerService {

    private final RestClientConfig restClientConfig;
    private final static Integer COUNT = 2;
    private final Random random = new Random();
    private static final String[] MENU = {
            "AMERICANO", "LATTE", "BREAD", "ICE CREAM"
    };

    @Transactional
    public void syncTest() {
        log.info("[CustomerService.syncTest] start");
        int count;
        int menu_num;
        for(int i=1;i<=COUNT;i++){
            log.info("[CustomerService.syncTest] {}번째 손님 입장", i);
            count = random.nextInt(10)+1;
            menu_num = random.nextInt(MENU.length);
            CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
            createOrderRequestDTO.setCustomerId(i);
            createOrderRequestDTO.setQuantity(count);
            createOrderRequestDTO.setMenu(MENU[menu_num]);
            ResponseEntity<GetOrderResponseDTO> getOrderResponseDTO = restClientConfig.restClient().post()
                    .uri(uriBuilder -> uriBuilder.path("/order/total").build())
                    .body(createOrderRequestDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new UserException(INTERNAL_SERVER_ERROR);
                    })
                    .toEntity(GetOrderResponseDTO.class);
            log.info("[CustomerService.syncTest] {}번째 손님 퇴장 : {} 메뉴", i,getOrderResponseDTO.getBody().getMenu());
        }
        log.info("[CustomerService.syncTest] end");
    }

    @Transactional
    public void asyncTest() {
        log.info("[CustomerService.asyncTest] start");
        int count;
        int menu_num;
        for(int i=1;i<=COUNT;i++){
            log.info("[CustomerService.syncTest] {}번째 손님 입장", i);
            count = random.nextInt(10)+1;
            menu_num = random.nextInt(MENU.length);
            CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
            createOrderRequestDTO.setCustomerId(i);
            createOrderRequestDTO.setQuantity(count);
            createOrderRequestDTO.setMenu(MENU[menu_num]);
            ResponseEntity<GetOrderResponseDTO> getOrderResponseDTO = restClientConfig.restClient().post()
                    .uri(uriBuilder -> uriBuilder.path("/order/total").build())
                    .body(createOrderRequestDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new UserException(INTERNAL_SERVER_ERROR);
                    })
                    .toEntity(GetOrderResponseDTO.class);
            log.info("[CustomerService.syncTest] {}번째 손님 퇴장 : {} 메뉴", i,getOrderResponseDTO.getBody().getMenu());
        }
        log.info("[CustomerService.asyncTest] end");
    }
}
