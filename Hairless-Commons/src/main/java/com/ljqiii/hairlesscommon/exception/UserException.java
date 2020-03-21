package com.ljqiii.hairlesscommon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UserException extends RuntimeException {

    @Getter
    @Setter
    int errcode = ERROR.UNKNOW.getCode();

    @Getter
    @AllArgsConstructor
    public enum ERROR {
        USER_ALREADY_EXISTS(1, "用户已存在"),
        USER_DISABLED(2, "用户被禁用"),
        INSERT_FAIL(3, "添加失败"),
        UNKNOW(10, "未知错误");

        int code;
        String errmsg;

    }

    public UserException(ERROR error) {
        super(error.getErrmsg());
        this.errcode = errcode;
    }

    public UserException(String message) {
        super(message);
    }
}
