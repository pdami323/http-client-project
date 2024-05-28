package com.http.client.httpclientproject.example.controller;

import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import com.http.client.httpclientproject.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Tag(name = "07. 주문 관리", description = "주문")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @Operation(summary = "주문", description = "주문")
    public ResponseEntity<GetOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDTO){
        orderService.createOrder(createOrderRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "개별 음료 제공", description = "개별 음료 제공")
    public ResponseEntity<GetOrderResponseDTO> getOrder(@PathVariable Integer orderId){
        CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
        createOrderRequestDTO.setOrderId(orderId);
        GetOrderResponseDTO getOrderResponseDTO = orderService.getOrderAndCook(createOrderRequestDTO);
        return new ResponseEntity<>(getOrderResponseDTO, HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "음료 제공", description = "음료 제공")
    public ResponseEntity<List<GetOrderResponseDTO>> getOrderList(){
        List<GetOrderResponseDTO> result = orderService.getOrderList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/total")
    @Operation(summary = "전체 로직", description = "전체 로직")
    public ResponseEntity<GetOrderResponseDTO> summary(@RequestBody CreateOrderRequestDTO createOrderRequestDTO){
        log.info("{}", createOrderRequestDTO);
        GetOrderResponseDTO getOrderResponseDTO = orderService.createOrder(createOrderRequestDTO);
        createOrderRequestDTO.setOrderId(getOrderResponseDTO.getOrderId());
        getOrderResponseDTO = orderService.getOrderAndCook(createOrderRequestDTO);
        return new ResponseEntity<>(getOrderResponseDTO, HttpStatus.OK);
    }
}
