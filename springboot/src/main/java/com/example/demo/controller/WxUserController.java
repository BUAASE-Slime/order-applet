package com.example.demo.controller;

import com.example.demo.api.ResultVO;
import com.example.demo.bean.UserInfo;
import com.example.demo.enums.ResultEnum;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserForm;
import com.example.demo.utils.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户相关
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class WxUserController {

    @Autowired
    UserRepository repository;

    //创建订单
    @PostMapping("/save")
    public ResultVO create(@Valid UserForm userForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, userForm={}", userForm);
            throw new DianCanException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfo user = new UserInfo();
        user.setUsername(userForm.getUsername());
        user.setOpenid(userForm.getOpenid());
        user.setPhone(userForm.getPhone());
        user.setZhuohao(userForm.getZhuohao());
        user.setRenshu(userForm.getRenshu());

        return ApiUtil.success(repository.save(user));
    }

    @GetMapping("/getUserInfo")
    public ResultVO getUserInfo(@RequestParam("openid") String openid) {
        UserInfo user = repository.findByOpenid(openid);
        return ApiUtil.success(user);
    }

}
