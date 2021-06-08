package com.example.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 返回给小程序的菜品页
 */
@Data
public class FoodRes {
    @JsonProperty("id")
    private Integer foodId;

    @JsonProperty("name")
    private String foodName;

    @JsonProperty("price")
    private BigDecimal foodPrice;
    @JsonProperty("stock")
    private Integer foodStock;//库存

    @JsonProperty("desc")
    private String foodDesc;

    @JsonProperty("icon")
    private String foodIcon;
}