package com.ljqiii.hairlessaccount.form;

import com.ljqiii.hairlesscommon.enums.PayMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NewVipForm {
    Integer nmonth;//开通几个月
    PayMethodEnum payMethod;
}
