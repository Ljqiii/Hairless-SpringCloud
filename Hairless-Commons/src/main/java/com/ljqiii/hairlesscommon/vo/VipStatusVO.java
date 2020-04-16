package com.ljqiii.hairlesscommon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VipStatusVO {
    boolean isVip;
    String expiredTime;
}
