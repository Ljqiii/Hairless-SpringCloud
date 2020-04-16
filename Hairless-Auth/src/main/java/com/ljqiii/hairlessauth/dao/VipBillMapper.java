package com.ljqiii.hairlessauth.dao;

import com.ljqiii.hairlesscommon.domain.VipBill;
import com.ljqiii.hairlesscommon.enums.PayStatusEnum;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface VipBillMapper {

    @Select("select count(0) from vipbill where username=#{username} and paystatus='Payed' and now() between vipstarttime and vipendtime")
    int ifUserVipNow(@Param("username") String username);
}
