package com.example.demo.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import study.backend.zb_spring_study.convpay.dto.PayCancelRequest;
import study.backend.zb_spring_study.convpay.dto.PayCancelResponse;
import study.backend.zb_spring_study.convpay.dto.PayRequest;
import study.backend.zb_spring_study.convpay.dto.PayResponse;
import study.backend.zb_spring_study.convpay.service.CardAdapter;
import study.backend.zb_spring_study.convpay.service.ConveniencePayService;
import study.backend.zb_spring_study.convpay.service.DiscountByConvenience;
import study.backend.zb_spring_study.convpay.service.MoneyAdapter;
import study.backend.zb_spring_study.convpay.type.ConvenienceType;
import study.backend.zb_spring_study.convpay.type.PayCancelResult;
import study.backend.zb_spring_study.convpay.type.PayMethodType;
import study.backend.zb_spring_study.convpay.type.PayResult;

import java.util.Arrays;
import java.util.HashSet;

class ConveniencePayServiceTest {
    ConveniencePayService conveniencePayService = new ConveniencePayService(
            new HashSet<>(
                    Arrays.asList(new MoneyAdapter(), new CardAdapter())
            ),
            new DiscountByConvenience()
    );

    @Test
    void pay_success() {
        //given
        PayRequest payRequest = new PayRequest(PayMethodType.MONEY, ConvenienceType.G25, 50);

        //when
        PayResponse payResponse = conveniencePayService.pay(payRequest);

        //then
        assertEquals(PayResult.SUCCESS, payResponse.getPayResult());
        assertEquals(40, payResponse.getPaidAmount());
    }

    @Test
    void pay_fail() {
        //given
        PayRequest payRequest = new PayRequest(PayMethodType.MONEY, ConvenienceType.G25, 1500_001);

        //when
        PayResponse payResponse = conveniencePayService.pay(payRequest);

        //then
        assertEquals(PayResult.FAIL, payResponse.getPayResult());
        assertEquals(0, payResponse.getPaidAmount());
    }

    @Test
    void pay_cancel_success() {
        //given
        PayCancelRequest payCancelRequest = new PayCancelRequest(PayMethodType.MONEY, ConvenienceType.G25, 1000);

        //when
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);

        //then
        assertEquals(PayCancelResult.PAY_CANCEL_SUCCESS, payCancelResponse.getPayCancelResult());
        assertEquals(1000, payCancelResponse.getPayCanceledAmount());
    }

    @Test
    void pay_cancel_fail() {
        //given
        PayCancelRequest payCancelRequest = new PayCancelRequest(PayMethodType.MONEY, ConvenienceType.G25, 99);

        //when
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);

        //then
        assertEquals(PayCancelResult.PAY_CANCEL_FAIL, payCancelResponse.getPayCancelResult());
        assertEquals(0, payCancelResponse.getPayCanceledAmount());
    }
}