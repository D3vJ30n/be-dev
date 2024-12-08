package study.backend.zb_spring_study.convpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import study.backend.zb_spring_study.convpay.service.CardAdapter;
import study.backend.zb_spring_study.convpay.service.ConveniencePayService;
import study.backend.zb_spring_study.convpay.service.DiscountByConvenience;
import study.backend.zb_spring_study.convpay.service.MoneyAdapter;

import java.util.Arrays;
import java.util.HashSet;

@Configuration // 스프링 설정 클래스임을 나타냄
public class ApplicationConfig {

    @Bean
    public ConveniencePayService conveniencePayService(){
        return new ConveniencePayService(
            new HashSet<>(Arrays.asList(moneyAdapter(), cardAdapter())),
            discountByConvenience()
        );
    }

    @Bean
    public CardAdapter cardAdapter() {
        return new CardAdapter();
    }

    @Bean
    public MoneyAdapter moneyAdapter() {
        return new MoneyAdapter();
    }

    @Bean
    public DiscountByConvenience discountByConvenience() {
        return new DiscountByConvenience();
    }
}