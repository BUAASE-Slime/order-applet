package com.example.demo.handle;

import com.example.demo.exception.DianCanAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DianCanExceptionHandler {


    //拦截登录异常
    //http://localhost:8080/diancan/leimu/list
    @ExceptionHandler(value = DianCanAuthorizeException.class)
    public String handlerAuthorizeException() {
        return "component/loginView";
    }
}
