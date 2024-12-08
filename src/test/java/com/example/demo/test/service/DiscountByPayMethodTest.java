package com.example.demo.test.service;

import org.junit.jupiter.api.Test;
import study.backend.zb_spring_study.convpay.dto.PayRequest;
import study.backend.zb_spring_study.convpay.service.DiscountByPayMethod;
import study.backend.zb_spring_study.convpay.type.ConvenienceType;
import study.backend.zb_spring_study.convpay.type.PayMethodType;

import static org.junit.jupiter.api.Assertions.*;

class DiscountByPayMethodTest {
    DiscountByPayMethod discountByPayMethod = new DiscountByPayMethod();

    @Test
    void discountSuccess() {
        //given
        PayRequest payRequestMoney = new PayRequest(PayMethodType.MONEY,
                ConvenienceType.G25, 1000);
        PayRequest payRequestCard = new PayRequest(PayMethodType.CARD,
                ConvenienceType.G25, 1000);

        //when
        Integer discountedAmountMoney =
                discountByPayMethod.getDiscountedAmount(payRequestMoney);
        Integer discountedAmountCard =
                discountByPayMethod.getDiscountedAmount(payRequestCard);

        //then
        assertEquals(700, discountedAmountMoney);
        assertEquals(1000, discountedAmountCard);
    }
}