package com.example.demo.verify;

import com.example.demo.exception.DianCanAuthorizeException;
import com.example.demo.global.GlobalConst;
import com.example.demo.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AuthorizeAspect {


    @Pointcut("execution(public * com.example.demo.controller.Admin*.*(..))")
    public void verify() {
    }

    //查询cookie
    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtil.get(request, GlobalConst.COOKIE_TOKEN);
        if (cookie == null) {
            log.warn("Cookie中查不到token");
            throw new DianCanAuthorizeException();
        }
    }
}
