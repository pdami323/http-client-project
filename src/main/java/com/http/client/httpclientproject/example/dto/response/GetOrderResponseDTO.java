package com.http.client.httpclientproject.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetOrderResponseDTO {
    @Schema(description = "주문 ID")
    private Integer orderId;

    @Schema(description = "메뉴")
    private String menu;

    @Schema(description = "주문 상태")
    private String orderStatus;
}
