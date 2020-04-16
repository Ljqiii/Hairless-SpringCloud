package com.ljqiii.hairlessaccount.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.ljqiii.hairlessaccount.form.NewVipForm;
import com.ljqiii.hairlessaccount.service.PaypalService;
import com.ljqiii.hairlessaccount.service.VipBillService;
import com.ljqiii.hairlesscommon.enums.CurrencyEnum;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.*;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.List;

@Controller
@Slf4j
public class VipController {


    @Value("${hairless.loginurl}")
    String hairlessLoginUrl;

    @Value("${hairless.vipFail}")
    String hairlessVipFail;

    @Value("${hairless.redirectUrl}")
    String hairlessRedirectUrl;


    @Value("${hairless.vipResult}")
    String hairlessVipResult;

    @Autowired
    VipBillService vipBillService;

    @Autowired
    PaypalService paypalService;

    /**
     * 开通vip，获取paypal付款链接
     * @param principal
     * @param newVipForm
     * @return
     */
    @PostMapping("/becomevip")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public @ResponseBody
    HairlessResponse<JSONObject> getPaypalApprovalUrl(Principal principal,
                                                      @RequestBody NewVipForm newVipForm) {
        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject jsonObject = new JSONObject();

        String name = principal.getName();
        int nMonth = newVipForm.getNmonth();

        try {
            String s = vipBillService.newVipPayBill(name, newVipForm.getPayMethod(), CurrencyEnum.USD, nMonth);
            if (!StringUtils.isEmpty(s)) {
                jsonObject.put("redirectUrl", s);
                response.setCodeMsg(ResultEnum.OK);
                response.setData(jsonObject);
                return response;
            }
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
            return response;
        }
    }

//    http://hairless.ljqiii.xyz/api/account/vippaypalcallback?paymentId=PAYID-L2L6P4Y8JV65176SM334663U&token=EC-1B265472F49389810&PayerID=EKYXK7FEHTNTA

    /**
     *paypal付款回调
     *
     * @param paymentId
     * @param token
     * @param payerID
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/vippaypalcallback")
    public String vipPaypalCallBack(@RequestParam("paymentId") String paymentId,
                                    @RequestParam("token") String token,
                                    @RequestParam("PayerID") String payerID) throws URISyntaxException {


        Integer id = vipBillService.callBack(paymentId, payerID);
        if (id != -1) {

            URIBuilder builder = new URIBuilder();
            builder.setHost(hairlessRedirectUrl);
            builder.setParameter("vipbillId", String.valueOf(id));
            builder.setParameter("url", hairlessVipResult);

            return "redirect://" + builder.build();
        } else {
            URIBuilder builder = new URIBuilder();
            builder.setHost(hairlessVipFail);
            String s = null;
            s = builder.build().toString();
            return "redirect:" + s;
        }
    }

    /**
     *
     * 开通vip的结果
     * @param principal
     * @param vipbillId
     * @return
     */
    @GetMapping("/resultinfo")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public @ResponseBody
    HairlessResponse<VipResultVO> getResultInfo(Principal principal,
                                                @RequestParam("vipbillId") String vipbillId) {
        String name = principal.getName();
        VipResultVO vipresult = vipBillService.getvipresult(name, vipbillId);
        HairlessResponse<VipResultVO> response = new HairlessResponse<>();
        if (vipresult != null) {
            response.setData(vipresult);
            response.setCodeMsg(ResultEnum.OK);
            return response;
        } else {
            response.setCodeMsg(ResultEnum.VIPBILLID_DONOT_EXIST);
            return response;
        }
    }

    @GetMapping("/vipstatus")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public @ResponseBody
    HairlessResponse<VipStatusVO> getVipStatus(Principal principal) {
        HairlessResponse<VipStatusVO> response = new HairlessResponse<>();

        VipStatusVO vipStatus = vipBillService.getVipStatus(principal.getName());
        response.setCodeMsg(ResultEnum.OK);
        response.setData(vipStatus);
        return response;
    }
}
