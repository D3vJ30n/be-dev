package com.example.demo.test.service;

import org.junit.jupiter.api.Test;
import study.backend.zb_spring_study.convpay.dto.PayRequest;
import study.backend.zb_spring_study.convpay.service.DiscountByConvenience;
import study.backend.zb_spring_study.convpay.type.ConvenienceType;
import study.backend.zb_spring_study.convpay.type.PayMethodType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountByConvenienceTest {
    DiscountByConvenience discountByConvenience = new DiscountByConvenience();

    @Test
    void discountTest() {
        //given
        PayRequest payRequestG25 =
                new PayRequest(PayMethodType.MONEY, ConvenienceType.G25, 1000);
        PayRequest payRequestGU =
                new PayRequest(PayMethodType.MONEY, ConvenienceType.GU, 1000);
        PayRequest payRequestSeven =
                new PayRequest(PayMethodType.MONEY, ConvenienceType.SEVEN, 1000);

        //when
        Integer discountedAmountG25 = discountByConvenience.getDiscountedAmount(payRequestG25);
        Integer discountedAmountGU = discountByConvenience.getDiscountedAmount(payRequestGU);
        Integer discountedAmountSeven = discountByConvenience.getDiscountedAmount(payRequestSeven);

        //then
        assertEquals(800, discountedAmountG25);
        assertEquals(900, discountedAmountGU);
        assertEquals(1000, discountedAmountSeven);
    }

}