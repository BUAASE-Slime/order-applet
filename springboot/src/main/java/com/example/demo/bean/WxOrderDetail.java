package com.example.demo.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 用户订单-订单详情
 */
@Entity
@Data
public class WxOrderDetail {
    @Id
    @GeneratedValue
    private Integer detailId;
    private Integer orderId;//订单id.
    private int foodId;//菜品id
    private String foodName;
    private BigDecimal foodPrice;
    private Integer foodQuantity;//下单食物数量
    private String foodIcon;//商品图
}
