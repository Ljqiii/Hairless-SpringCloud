package com.ljqiii.hairlessaccount.service;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlessaccount.dao.VipBillMapper;
import com.ljqiii.hairlesscommon.domain.VipBill;
import com.ljqiii.hairlesscommon.enums.CurrencyEnum;
import com.ljqiii.hairlesscommon.enums.PayMethodEnum;
import com.ljqiii.hairlesscommon.enums.PayStatusEnum;
import com.ljqiii.hairlesscommon.vo.VipResultVO;
import com.ljqiii.hairlesscommon.vo.VipStatusVO;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class VipBillService {

    @Autowired
    VipBillMapper vipBillMapper;

    @Autowired
    PaypalService paypalService;


    @Value("${vip.unitprice}")
    double vipUnitPirce;

    /**
     * 调用paypal
     * @param username
     * @param payMethod
     * @param currency
     * @param n
     * @return
     */
    public String newVipPayBill(String username, PayMethodEnum payMethod, CurrencyEnum currency, int n) {
        VipBill.VipBillBuilder builder = VipBill.builder();
        builder.userName(username).paystatus(PayStatusEnum.NotPayed).currency(currency).payMethod(payMethod).createTime(new Date());
        builder.totalMoney(n * vipUnitPirce);

        if (isVipNow(username)) {
            Date beforeVipEnd = vipBillMapper.selectVipEndTimeByUserName(username);
            beforeVipEnd = DateUtils.addDays(beforeVipEnd, 1);

            Date vipEnd = new Date(beforeVipEnd.getTime());
            vipEnd = DateUtils.addMonths(vipEnd, n);
            builder.vipStartTime(beforeVipEnd);
            builder.vipEndTime(vipEnd);
        } else {
            Date nowStart = new Date();
            nowStart = DateUtils.setHours(nowStart, 0);
            nowStart = DateUtils.setMinutes(nowStart, 0);
            nowStart = DateUtils.setMilliseconds(nowStart, 0);
            nowStart = DateUtils.setSeconds(nowStart, 0);

            Date nMonthAfter = new Date(nowStart.getTime());
            nMonthAfter = DateUtils.addMonths(nMonthAfter, n);
            builder.vipStartTime(nowStart);
            builder.vipEndTime(nMonthAfter);
        }
        VipBill vipBill = builder.build();


        try {
            // Create payment
            Payment payment = paypalService.createPayment(
                    n * vipUnitPirce,
                    currency.getCurrency(),
                    PayMethodEnum.paypal,
                    "Hairless Vip");
            System.out.println("createPayment=" + payment);
            vipBill.setPaymentId(payment.getId());
            vipBillMapper.insertVipBill(vipBill);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equalsIgnoreCase("approval_url")) {
                    return links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            log.error("paypal error", e);
        }
        return null;
    }

    public boolean isVipNow(String username) {
        return (vipBillMapper.ifUserVipNow(username) >= 1);
    }


    /**
     * paybill设置支付状态
     *
     * @param payBillId
     */
    public void setPayBillOk(String payBillId, String payerEmail, String payerName) {
        VipBill vipBill = vipBillMapper.selectVipBill(payBillId);
        if (vipBill == null) {
            throw new IllegalArgumentException("paybill 不存在");
        }
        vipBillMapper.updatePayStatus(vipBill, PayStatusEnum.Payed, payerEmail, payerName);
    }

    /**
     * paybill设置未支付状态
     *
     * @param payBillId
     */
    public void setPayBillCancal(String payBillId) {
        VipBill vipBill = vipBillMapper.selectVipBill(payBillId);
        if (vipBill == null) {
            throw new IllegalArgumentException("paybill 不存在");
        }
        vipBillMapper.updatePayStatus(vipBill, PayStatusEnum.Cancel, null, null);
    }

    /**
     * callback
     *
     * @param paymentId
     * @param PayerID
     * @return
     */
    public Integer callBack(String paymentId, String PayerID) {
        Payment payment = null;
        try {
            payment = paypalService.executePayment(paymentId, PayerID);
            JSONObject payerInfo = JSONObject.parseObject(JSONObject.toJSONString(payment.getPayer())).getJSONObject("payerInfo");
            String payerEmail = payerInfo.getString("email");

            String firstName = payerInfo.getString("firstName");
            String lastName = payerInfo.getString("lastName");
            String payerName = firstName + " " + lastName;

            setPayBillOk(paymentId, payerEmail, payerName);
            int Id = vipBillMapper.selectIdByPaymentid(paymentId);
            return Id;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            //TODO:退款
        }
        return -1;
    }

    /**
     * vip开通结果
     * @param username
     * @param vipbillId
     * @return
     */
    public VipResultVO getvipresult(String username, String vipbillId) {
        return vipBillMapper.selectVipResultByUsernameAndVipbillId(username, vipbillId);
    }

    /**
     * 当前vip状态
     * @param username
     * @return
     */
    public VipStatusVO getVipStatus(String username) {
        String username1 = username;
        int i = vipBillMapper.ifUserVipNow(username1);
        VipStatusVO vipStatusVO = new VipStatusVO();
        vipStatusVO.setVip(i >= 1);
        if (i >= 1) {
            SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy年MM月dd日");
            vipStatusVO.setExpiredTime(dateFmt.format(vipBillMapper.selectVipEndTimeByUserName(username)));
        } else {
            vipStatusVO.setExpiredTime("0");
        }
        return vipStatusVO;
    }
}
