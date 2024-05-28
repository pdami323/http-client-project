package com.http.client.httpclientproject.example.service;

import com.http.client.httpclientproject.common.constant.CommonConstant;
import com.http.client.httpclientproject.common.exception.BizException;
import com.http.client.httpclientproject.common.exception.ErrorCode;
import com.http.client.httpclientproject.example.dao.OrderDAO;
import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import com.http.client.httpclientproject.example.vo.TbOrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDAO orderDAO;
    @Transactional
    public GetOrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO) {
        log.info("[OrderService.createOrder] start");
        GetOrderResponseDTO getOrderResponseDTO = new GetOrderResponseDTO();
        log.info("[OrderService.createOrder] 1. 주문 등록 start");
        try {
            createOrderRequestDTO.setOrderStatus(CommonConstant.ORDER_STATUS_WAITING);
            orderDAO.createOrder(createOrderRequestDTO);
        }catch (Exception e){
            log.error("[OrderService.createOrder] 주문 등록 중 오류가 발생했습니다., {}", e.getMessage());
            throw new BizException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.createOrder] 1. 주문 등록 end");
        getOrderResponseDTO.setOrderId(createOrderRequestDTO.getOrderId());
        log.info("[OrderService.createOrder] end, {}", createOrderRequestDTO.getOrderId());
        return getOrderResponseDTO;
    }

    @Transactional
    public GetOrderResponseDTO getOrderResponseDTO(CreateOrderRequestDTO createOrderRequestDTO) {
        log.info("[OrderService.getOrder] start");
        GetOrderResponseDTO result = new GetOrderResponseDTO();
        if (createOrderRequestDTO.getQuantity()==null){
            createOrderRequestDTO.setQuantity(1);
        }
        log.info("[OrderService.getOrder] 1. 음식 준비 start");
        try {
            Thread.sleep(1000L * createOrderRequestDTO.getQuantity());
        }catch (Exception e){
            log.error("[OrderService.getOrder] 음식 준비 중 오류가 발생했습니다., {}", e.getMessage());
            throw new BizException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.getOrder] 1. 음식 준비 end");

        log.info("[OrderService.getOrder] 2. 주문 상태 변경 start");
        try {
            TbOrderVO tbOrderVO = new TbOrderVO();
            tbOrderVO.setOrderId(createOrderRequestDTO.getOrderId());
            tbOrderVO.setOrderStatus(CommonConstant.ORDER_STATUS_COMPLETE);
            orderDAO.modifyOrder(tbOrderVO);
        }catch (Exception e){
            log.error("[OrderService.getOrder] 주문상태 변경 중 오류가 발생했습니다., {}", e.getMessage());
            throw new BizException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.getOrder] 2. 주문 상태 변경 end");

        log.info("[OrderService.getOrder] 3. 주문 정보 조회 start");
        try {
            result = orderDAO.getOrder(createOrderRequestDTO.getOrderId());
        }catch (Exception e){
            log.error("[OrderService.getOrder] 주문정보 조회 중 오류가 발생했습니다., {}", e.getMessage());
            throw new BizException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.getOrder] 3. 주문 정보 조회 end");
        log.info("[OrderService.getOrder] end, {}", result);
        return result;
    }
}
