package com.example.demo.response;

import lombok.Data;

@Data
public class WxCardResponse {

    /**
     * 商品Id.
     */
    private int productId;

    /**
     * 数量.
     */
    private Integer productQuantity;

    public WxCardResponse(int productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
