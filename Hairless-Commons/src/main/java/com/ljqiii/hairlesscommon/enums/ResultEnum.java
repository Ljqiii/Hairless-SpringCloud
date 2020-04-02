package com.ljqiii.hairlesscommon.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    OK(200, "success"),
    PARAM_ERROR(300, "参数错误"),
    SERVER_ERROR(500, "服务器错误"),
    UNAUTHORIZED(401, "Authentication Faild"),
    LOGIN_SUCCESS(200, "Login Success");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    boolean isOk() {
        return this.getMessage().equals("success");
    }
}
