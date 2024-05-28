package com.http.client.httpclientproject.example.dao;

import com.http.client.httpclientproject.example.dto.request.CreateOrderRequestDTO;
import com.http.client.httpclientproject.example.dto.response.GetOrderResponseDTO;
import com.http.client.httpclientproject.example.vo.TbOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderDAO {
    Integer createOrder(CreateOrderRequestDTO createOrderRequestDTO);

    Integer modifyOrder(TbOrderVO tbOrderVO);

    GetOrderResponseDTO getOrder(Integer orderId);
}
