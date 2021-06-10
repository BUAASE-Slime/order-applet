package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeNumEnum {

    //0"新订单，未支付;1"新订单，已支付";2, "已取消"；3"待评价"；4“已完成”
    NEW(0, "未支付"),
    NEW_PAYED(1, "已支付"),
    CANCEL(2, "已取消"),
    FINISHED(3, "已完结"),//已完结待评价
    COMMENT(4, "已评价"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
