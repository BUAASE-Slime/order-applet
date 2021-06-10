package com.example.demo.xiaochengxuadmin;

import com.example.demo.bean.AdminInfo;
import com.example.demo.global.GlobalConst;
import com.example.demo.repository.AdminRepository;
import com.example.demo.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class XcxLoginAdminController {

    @Autowired
    AdminRepository repository;

    @PostMapping("/xcxlogin")
    @ResponseBody
    public int loginAdmin(@RequestParam(value = "username") String phoneOrname,
                             @RequestParam(value = "password") String password,
                             HttpServletResponse response) {
        //这里得phoneOrname代表 手机号或者用户名
        System.out.println("执行了登陆查询");
        AdminInfo admin = repository.findByPhoneOrUsername(phoneOrname, phoneOrname);
        log.info("查询到得admininfo={}", admin);
        if (admin != null && admin.getPassword().equals(password)) {
            log.info("登录成功的token={}", admin.getAdminId());//用adminid做cookie
            //有效期2小时
            CookieUtil.set(response, GlobalConst.COOKIE_TOKEN, "" + admin.getAdminId(), 7200);
            return 1;
        } else {
            return 0;
        }
    }
}
