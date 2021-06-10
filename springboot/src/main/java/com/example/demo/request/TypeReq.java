package com.example.demo.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class TypeReq {

    private Integer leimuId;
    @NotEmpty(message = "类目名必填")
    private String leimuName;//类目名字
    @NotNull(message = "类目type必填")
    private Integer leimuType;//类目编号

}
