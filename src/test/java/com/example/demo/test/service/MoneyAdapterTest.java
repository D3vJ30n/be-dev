package com.example.demo.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import study.backend.zb_spring_study.convpay.service.MoneyAdapter;
import study.backend.zb_spring_study.convpay.type.MoneyUseCancelResult;
import study.backend.zb_spring_study.convpay.type.MoneyUseResult;

import static study.backend.zb_spring_study.convpay.type.MoneyUseResult.USE_FAIL;
import static study.backend.zb_spring_study.convpay.type.MoneyUseResult.USE_SUCCESS;

class MoneyAdapterTest {
    MoneyAdapter moneyAdapter = new MoneyAdapter();

    @Test
    void money_use_fail() {
        //given
        Integer payAmount = 1000_001;

        //when
        MoneyUseResult moneyUseResult = moneyAdapter.use(payAmount);

        //then
        assertEquals(USE_FAIL, moneyUseResult);
    }

    @Test
    void money_use_success() {
        //given
        Integer payAmount = 1000_000;

        //when
        MoneyUseResult moneyUseResult = moneyAdapter.use(payAmount);

        //then
        assertEquals(USE_SUCCESS, moneyUseResult);
    }

    @Test
    void money_use_cancel_success() {
        //given
        Integer payCancelAmount = 101;

        //when
        MoneyUseCancelResult moneyUseCancelResult = moneyAdapter.useCancel(payCancelAmount);

        //then
        assertEquals(MoneyUseCancelResult.MONEY_USE_CANCEL_SUCCESS, moneyUseCancelResult);
    }

    @Test
    void money_use_cancel_fail() {
        //given
        Integer payCancelAmount = 99;

        //when
        MoneyUseCancelResult moneyUseCancelResult = moneyAdapter.useCancel(payCancelAmount);

        //then
        assertEquals(MoneyUseCancelResult.MONEY_USE_CANCEL_FAIL, moneyUseCancelResult);
    }
}