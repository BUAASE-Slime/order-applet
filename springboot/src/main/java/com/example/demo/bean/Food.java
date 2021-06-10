package com.example.demo.bean;

import com.example.demo.enums.FoodStatusEnum;
import com.example.demo.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Food {
    @Id
    @GeneratedValue
    private Integer foodId;//菜品id
    private String foodName;//菜品名
    private BigDecimal foodPrice;//菜品单价
    private Integer foodStock;//库存
    private String foodDesc;//菜品描述
    private String foodIcon;//菜品图
    private Integer foodStatus = FoodStatusEnum.UP.getCode();//状态, 0正常1下架.

    private Integer leimuType;//菜品类目编号
    private Integer adminId;//菜品属于那个商家

    @CreatedDate//自动添加创建时间的注解
    private Date createTime;
    @LastModifiedDate//自动添加更新时间的注解
    private Date updateTime;

    @JsonIgnore
    public FoodStatusEnum getFoodStatusEnum() {
        return EnumUtil.getByCode(foodStatus, FoodStatusEnum.class);
    }

}
