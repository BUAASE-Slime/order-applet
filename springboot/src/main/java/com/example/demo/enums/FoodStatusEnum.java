package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum FoodStatusEnum implements CodeNumEnum {
    UP(0, "在架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    FoodStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
