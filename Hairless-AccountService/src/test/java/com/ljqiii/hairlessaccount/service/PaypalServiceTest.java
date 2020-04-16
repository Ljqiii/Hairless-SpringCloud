package com.ljqiii.hairlessaccount.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaypalServiceTest {

    @Autowired
    PaypalService paypalService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void executePayment() {
        try {
            Payment ekyxk7FEHTNTA = paypalService.executePayment("PAYID-L2L6P4Y8JV65176SM334663U", "EKYXK7FEHTNTA");
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        int a=1;
    }
}
