package com.example.demo.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserForm {
    //买家姓名
    @NotEmpty(message = "姓名必填")
    private String username;
    //买家手机号
    @NotEmpty(message = "手机号必填")
    private String phone;
    //买家微信openid
    @NotEmpty(message = "openid必填")
    private String openid;

    private String zhuohao;
    private String renshu;
}
