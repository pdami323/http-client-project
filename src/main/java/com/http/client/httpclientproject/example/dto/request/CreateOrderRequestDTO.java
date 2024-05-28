package com.http.client.httpclientproject.example.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateOrderRequestDTO {

    @Schema(description = "주문자 ID")
    private Integer customerId;

    @Schema(description = "메뉴")
    private String menu;

    @Schema(description = "수량")
    private Integer quantity;

    @JsonIgnore
    @Schema(description = "주문 상태")
    private String orderStatus;

    @JsonIgnore
    @Schema(description = "주문ID")
    private Integer orderId;

}
