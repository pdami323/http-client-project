package com.http.client.httpclientproject.example.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbOrderVO {
    private Integer orderId;
    private Integer customerId;
    private String menu;
    private Integer quantity;
    private String orderStatus;
}
