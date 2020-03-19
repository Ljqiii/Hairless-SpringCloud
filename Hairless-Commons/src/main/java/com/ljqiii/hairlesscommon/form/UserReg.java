package com.ljqiii.hairlesscommon.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserReg {

    @NotEmpty(message = "用户名不能为空")
    String username;

    @NotEmpty(message = "昵称不能为空")
    String nickname;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码应该在6-20位数之间")
    String password;

    @Email(message = "邮箱格式不正确")
    String email;

    @Pattern(regexp = "normaluser|teacher", message = "角色错误")
    String role;

}
