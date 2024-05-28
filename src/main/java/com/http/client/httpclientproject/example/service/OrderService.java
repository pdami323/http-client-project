package com.http.client.httpclientproject.example.service;

import com.http.client.httpclientproject.common.constant.CommonConstant;
import com.http.client.httpclientproject.common.exception.UserException;
import com.http.client.httpclientproject.common.exception.ErrorCode;
import com.http.client.httpclientproject.example.dao.OrderDAO;
import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import com.http.client.httpclientproject.example.vo.TbOrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.http.client.httpclientproject.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDAO orderDAO;

    public GetOrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO) {
        log.info("[OrderService.createOrder] start, {} 메뉴 - {}개", createOrderRequestDTO.getMenu(), createOrderRequestDTO.getQuantity());
        GetOrderResponseDTO getOrderResponseDTO = new GetOrderResponseDTO();
        log.info("[OrderService.createOrder] 1. 주문 등록 start");
        try {
            createOrderRequestDTO.setOrderStatus(CommonConstant.ORDER_STATUS_WAITING);
            orderDAO.createOrder(createOrderRequestDTO);
        }catch (UserException e){
            log.error("[OrderService.createOrder] 주문 등록 중 오류가 발생했습니다., {}", e.getMessage());
            throw new UserException(INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.createOrder] 1. 주문 등록 end");
        getOrderResponseDTO.setOrderId(createOrderRequestDTO.getOrderId());
        log.info("[OrderService.createOrder] end, {}", createOrderRequestDTO.getOrderId());
        return getOrderResponseDTO;
    }

    public GetOrderResponseDTO getOrderAndCook(CreateOrderRequestDTO createOrderRequestDTO) {
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
            throw new UserException(INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.getOrder] 1. 음식 준비 end");

        log.info("[OrderService.getOrder] 2. 주문 상태 변경 start");
        try {
            TbOrderVO tbOrderVO = new TbOrderVO();
            tbOrderVO.setOrderId(createOrderRequestDTO.getOrderId());
            tbOrderVO.setOrderStatus(CommonConstant.ORDER_STATUS_COMPLETE);
            orderDAO.modifyOrder(tbOrderVO);
        }catch (UserException e){
            log.error("[OrderService.getOrder] 주문상태 변경 중 오류가 발생했습니다., {}", e.getMessage());
            throw new UserException(INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.getOrder] 2. 주문 상태 변경 end");

        log.info("[OrderService.getOrder] 3. 주문 정보 조회 start");
        try {
            result = orderDAO.getOrder(createOrderRequestDTO.getOrderId());
        }catch (UserException e){
            log.error("[OrderService.getOrder] 주문정보 조회 중 오류가 발생했습니다., {}", e.getMessage());
            throw new UserException(INTERNAL_SERVER_ERROR);
        }
        log.info("[OrderService.getOrder] 3. 주문 정보 조회 end");
        log.info("[OrderService.getOrder] end, {}", result);
        return result;
    }

    public List<GetOrderResponseDTO> getOrderList() {
        log.info("[OrderService.getOrderList] start");
        List<GetOrderResponseDTO> result = new ArrayList<>();
        log.info("[OrderService.getOrderList] 1. 미완료 주문 조회 start");
        List<GetOrderResponseDTO> cookList = new ArrayList<>();
        try {
            cookList = orderDAO.getOrderList();
        }catch (Exception e){
            log.error("[OrderService.getOrderList] 미완료 주문 조회 중 오류가 발생했습니다.");
            throw new UserException(INTERNAL_SERVER_ERROR);
        }
        if(cookList.isEmpty()){
            log.info("[OrderService.getOrderList] 모든 주문이 완료되었습니다.");
            return result;
        }
        log.info("[OrderService.getOrderList] 1. 미완료 주문 조회 end");
        for (GetOrderResponseDTO dto : cookList){
            log.info("[OrderService.getOrderList] 2. 음식 준비 start, {} 메뉴 - {}개", dto.getMenu(), dto.getQuantity());
            try {
                Thread.sleep(1000L * dto.getQuantity());
            }catch (Exception e){
                log.error("[OrderService.getOrder] 음식 준비 중 오류가 발생했습니다., {}", e.getMessage());
                throw new UserException(INTERNAL_SERVER_ERROR);
            }
            log.info("[OrderService.getOrderList] 2. 음식 준비 end");
            log.info("[OrderService.getOrderList] 3. 주문 상태 변경 start");
            try {
                TbOrderVO tbOrderVO = new TbOrderVO();
                tbOrderVO.setOrderId(dto.getOrderId());
                tbOrderVO.setOrderStatus(CommonConstant.ORDER_STATUS_COMPLETE);
                log.info("{}", tbOrderVO);
                orderDAO.modifyOrder(tbOrderVO);
            }catch (UserException e){
                log.error("[OrderService.getOrder] 주문상태 변경 중 오류가 발생했습니다., {}", e.getMessage());
                throw new UserException(INTERNAL_SERVER_ERROR);
            }
            log.info("[OrderService.getOrderList] 3. 주문 상태 변경 end");
            result.add(dto);
        }
        log.info("[OrderService.getOrderList] end");
        return result;
    }
}
