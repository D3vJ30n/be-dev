package study.backend.zb_spring_study.convpay.service;

import study.backend.zb_spring_study.convpay.dto.PayRequest;

public interface DiscountInterface {
    Integer getDiscountedAmount(PayRequest payRequest);
}
