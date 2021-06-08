package com.example.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品(包含类目)
 */

@Data
public class TypeVO {
    @JsonProperty("name")
    private String TypeName;

    @JsonProperty("type")
    private Integer TypeIdx;

    @JsonProperty("foods")
    private List<FoodRes> foodResList;
}
