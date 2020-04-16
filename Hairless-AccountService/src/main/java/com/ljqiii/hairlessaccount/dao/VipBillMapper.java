package com.ljqiii.hairlessaccount.dao;

import com.ljqiii.hairlesscommon.domain.VipBill;
import com.ljqiii.hairlesscommon.enums.PayStatusEnum;
import com.ljqiii.hairlesscommon.vo.VipResultVO;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface VipBillMapper {

    @Select("select count(0) from vipbill where username=#{username} and paystatus='Payed' and now() between vipstarttime and vipendtime")
    int ifUserVipNow(@Param("username") String username);

    @Insert("insert into vipbill (username,paymethod,paymentid,paystatus,totalmoney,currency,createtime,vipstarttime,vipendtime)values(#{userName},#{payMethod},#{paymentId},#{paystatus},#{totalMoney},#{currency},#{createTime},#{vipStartTime},#{vipEndTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertVipBill(VipBill vipBill);

    @Select("select id from vipbill where paymentid=#{paymentid}")
    int selectIdByPaymentid(@Param("paymentid") String paymentid);

    @Update("update vipbill set paystatus=#{payStatus},payerEmail=#{payerEmail},payerName=#{payerName} where paymentid=#{vipbill.paymentId}")
    int updatePayStatus(@Param("vipbill") VipBill vipBill, @Param("payStatus") PayStatusEnum payStatus, @Param("payerEmail") String payerEmail, @Param("payerName") String payerName);

    @Select("select vipendtime from vipbill where username=#{username} and paystatus='Payed' order by vipendtime desc limit 1 ")
    Date selectVipEndTimeByUserName(@Param("username") String username);

    @Select("select * from vipbill where paymentid=#{paymentId}")
    VipBill selectVipBill(@Param("paymentId") String paymentId);

    @Select("select vipendtime,payeremail,payername,paymethod from vipbill where username=#{username} and id=#{vipbillId}")
    VipResultVO selectVipResultByUsernameAndVipbillId(@Param("username") String username, @Param("vipbillId") String vipbillId);


}
