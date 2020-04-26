package com.ljqiii.hairlesscommon.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    OK(200, "success"),
    PARAM_ERROR(300, "参数错误"),
    SERVER_ERROR(500, "服务器错误"),
    UNAUTHORIZED(401, "Authentication Faild"),
    LOGIN_SUCCESS(200, "Login Success"),
    LOGOUT_SUCCESS(200, "Logout Success"),
    VIP_ONLY(1001, "仅Vip用户可以查看"),
    PROBLEM_DONOT_EXIST(1002, "问题不存在"),
    VIPPROBLEM_UNLOGIN(1003, "Vip题目需要登录后才能查看"),
    FAVORITE_FOLDER_EXIST(1004, "问题不存在"),
    VIPBILLID_DONOT_EXIST(1005, "vip订单号不存在"),
    PASSEORD_NOT_SAME(1006, "密码不一致"),
    CHANGE_PASSEORD_OK(1007, "修改密码成功"),
    PEOBLEM_ID_INVALID(1006, "题目编号必须为数字");

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
