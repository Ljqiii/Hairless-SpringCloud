package com.ljqiii.hairlessaccount.dao;

import com.ljqiii.hairlesscommon.domain.VipBill;
import com.ljqiii.hairlesscommon.enums.CurrencyEnum;
import com.ljqiii.hairlesscommon.enums.PayMethodEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VipBillMapperTest {

    @Autowired
    VipBillMapper vipBillMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void insertVipBill() {

        VipBill a = VipBill.builder().payMethod(PayMethodEnum.paypal).userName("a")
                .totalMoney(0.1).currency(CurrencyEnum.USD).vipEndTime(new Date()).vipEndTime(new Date())
                .build();
        vipBillMapper.insertVipBill(a);
        int aa = 1;

    }
}
