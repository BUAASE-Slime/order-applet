package com.example.demo.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalMoney {
    private String time;
    private BigDecimal totalMoney;
}