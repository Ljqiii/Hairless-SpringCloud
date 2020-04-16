package com.ljqiii.hairlesscommon.domain;

import com.ljqiii.hairlesscommon.enums.CurrencyEnum;
import com.ljqiii.hairlesscommon.enums.PayMethodEnum;
import com.ljqiii.hairlesscommon.enums.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VipBill {
    Integer id;
    String userName;
    PayMethodEnum payMethod;
    String paymentId;
    PayStatusEnum paystatus;
    double totalMoney;
    CurrencyEnum currency;
    Date createTime;
    Date vipStartTime;
    Date vipEndTime;
    String payerEmail;
    String payerName;
}
