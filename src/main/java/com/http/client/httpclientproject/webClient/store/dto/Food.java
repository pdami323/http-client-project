package com.http.client.httpclientproject.webClient.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private String foodName;
    private Integer calories;
    private Integer price;
}
