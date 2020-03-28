package com.ljqiii.hairlesscommon.domain.amqpdomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo implements Serializable {
    private static final long serialVersionUID = -1606246964070359580L;

    String username;
    Date logintime;
}
