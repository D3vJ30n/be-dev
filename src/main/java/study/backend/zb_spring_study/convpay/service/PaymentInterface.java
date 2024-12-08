package study.backend.zb_spring_study.convpay.service;

import study.backend.zb_spring_study.convpay.type.CancelPaymentResult;
import study.backend.zb_spring_study.convpay.type.PayMethodType;
import study.backend.zb_spring_study.convpay.type.PaymentResult;

public interface PaymentInterface {
    PayMethodType getPayMethodType();
    PaymentResult payment(Integer payAmount);
    CancelPaymentResult cancelPayment(Integer cancelAmount);
}
