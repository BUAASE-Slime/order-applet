package com.example.demo.exception;

import com.example.demo.enums.ResultEnum;

public class DianCanException extends RuntimeException {

    private Integer code;

    public DianCanException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public DianCanException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
