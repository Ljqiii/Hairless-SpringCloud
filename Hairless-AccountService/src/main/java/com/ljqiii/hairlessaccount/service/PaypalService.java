package com.ljqiii.hairlessaccount.service;

import com.ljqiii.hairlesscommon.enums.PayMethodEnum;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {


    @Value("${paypal.cancelUrl}")
    String cancelUrl;

    @Value("${paypal.successUrl}")
    String successUrl;

    APIContext apiContext;

    @Autowired
    public PaypalService(@Value("${paypal.clientID}") String clientID, @Value("${paypal.clientSecret}") String clientSecret, @Value("${paypal.mode}") String mode) {
        this.apiContext = new APIContext(clientID, clientSecret, mode);
    }


    public Payment createPayment(
            Double total,
            String currency,
            PayMethodEnum method,
            String description) throws PayPalRESTException {
        // Payment amount
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));
        // Transaction information
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        // Add transaction to a list
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        // Set payer details
        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());
        // Set redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        // Add payment details
        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    /**
     *
     *
     * {
     *   "id": "PAYID-L2L6P4Y8JV65176SM334663U",
     *   "intent": "sale",
     *   "payer": {
     *     "payment_method": "paypal",
     *     "status": "VERIFIED",
     *     "payer_info": {
     *       "email": "sb-gk4343h1464502@business.example.com",
     *       "first_name": "John",
     *       "last_name": "Doe",
     *       "payer_id": "EKYXK7FEHTNTA",
     *       "country_code": "C2",
     *       "shipping_address": {
     *         "recipient_name": "Doe John",
     *         "line1": "NO 1 Nan Jin Road",
     *         "city": "Shanghai",
     *         "country_code": "C2",
     *         "postal_code": "200000",
     *         "state": "Shanghai"
     *       }
     *     }
     *   },
     *   "cart": "1B265472F49389810",
     *   "transactions": [
     *     {
     *       "related_resources": [
     *         {
     *           "sale": {
     *             "id": "5SV161765U3312522",
     *             "amount": {
     *               "currency": "USD",
     *               "total": "0.10",
     *               "details": {
     *                 "subtotal": "0.10",
     *                 "shipping": "0.00",
     *                 "handling_fee": "0.00",
     *                 "shipping_discount": "0.00",
     *                 "insurance": "0.00"
     *               }
     *             },
     *             "payment_mode": "INSTANT_TRANSFER",
     *             "state": "completed",
     *             "protection_eligibility": "ELIGIBLE",
     *             "protection_eligibility_type": "ITEM_NOT_RECEIVED_ELIGIBLE,UNAUTHORIZED_PAYMENT_ELIGIBLE",
     *             "transaction_fee": {
     *               "currency": "USD",
     *               "value": "0.10"
     *             },
     *             "parent_payment": "PAYID-L2L6P4Y8JV65176SM334663U",
     *             "create_time": "2020-04-16T05:22:08Z",
     *             "update_time": "2020-04-16T05:22:08Z",
     *             "links": [
     *               {
     *                 "href": "https://api.sandbox.paypal.com/v1/payments/sale/5SV161765U3312522",
     *                 "rel": "self",
     *                 "method": "GET"
     *               },
     *               {
     *                 "href": "https://api.sandbox.paypal.com/v1/payments/sale/5SV161765U3312522/refund",
     *                 "rel": "refund",
     *                 "method": "POST"
     *               },
     *               {
     *                 "href": "https://api.sandbox.paypal.com/v1/payments/payment/PAYID-L2L6P4Y8JV65176SM334663U",
     *                 "rel": "parent_payment",
     *                 "method": "GET"
     *               }
     *             ]
     *           }
     *         }
     *       ],
     *       "amount": {
     *         "currency": "USD",
     *         "total": "0.10",
     *         "details": {
     *           "subtotal": "0.10",
     *           "shipping": "0.00",
     *           "handling_fee": "0.00",
     *           "shipping_discount": "0.00",
     *           "insurance": "0.00"
     *         }
     *       },
     *       "payee": {
     *         "email": "sb-b3dxt1458352@personal.example.com",
     *         "merchant_id": "VMCJGX6S2WVZQ"
     *       },
     *       "description": "Hairless Vip",
     *       "item_list": {
     *         "shipping_address": {
     *           "recipient_name": "Doe John",
     *           "line1": "NO 1 Nan Jin Road",
     *           "city": "Shanghai",
     *           "country_code": "C2",
     *           "postal_code": "200000",
     *           "state": "Shanghai"
     *         }
     *       }
     *     }
     *   ],
     *   "failed_transactions": [],
     *   "state": "approved",
     *   "create_time": "2020-04-16T05:06:58Z",
     *   "update_time": "2020-04-16T05:22:08Z",
     *   "links": [
     *     {
     *       "href": "https://api.sandbox.paypal.com/v1/payments/payment/PAYID-L2L6P4Y8JV65176SM334663U",
     *       "rel": "self",
     *       "method": "GET"
     *     }
     *   ]
     * }
     */



    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        Payment execute = payment.execute(apiContext, paymentExecute);
        return execute;
    }


}
