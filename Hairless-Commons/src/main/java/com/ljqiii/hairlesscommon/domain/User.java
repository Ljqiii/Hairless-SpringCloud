package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User  {
    Integer id;
    String userName;
    String nickName;
    String encodedPassword;
    String eMail;
    boolean isAccountExpired;
    boolean isAccountLocked;
    boolean isCredentialsExpired;
    boolean isEnabled;

    boolean isVip;
    String avatar;

    List<Role> authorities;
}
