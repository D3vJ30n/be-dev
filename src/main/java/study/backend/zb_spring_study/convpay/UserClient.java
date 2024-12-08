package study.backend.zb_spring_study.convpay;

// 필요한 스프링 및 애플리케이션 구성 관련 클래스 임포트
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import study.backend.zb_spring_study.convpay.config.ApplicationConfig;
import study.backend.zb_spring_study.convpay.dto.PayCancelRequest;
import study.backend.zb_spring_study.convpay.dto.PayCancelResponse;
import study.backend.zb_spring_study.convpay.dto.PayRequest;
import study.backend.zb_spring_study.convpay.dto.PayResponse;
import study.backend.zb_spring_study.convpay.service.ConveniencePayService;
import study.backend.zb_spring_study.convpay.type.ConvenienceType;
import study.backend.zb_spring_study.convpay.type.PayMethodType;

public class UserClient {
    public static void main(String[] args) {
        // 사용자(Client)가 결제와 결제 취소를 요청하는 메인 클래스

        // Spring의 ApplicationContext 생성
        // AnnotationConfigApplicationContext는 자바 기반의 설정 클래스를 사용해 컨텍스트를 초기화함
        // ApplicationConfig.class는 스프링 빈(Bean) 설정을 포함하는 클래스
        ApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(ApplicationConfig.class);

        // 컨테이너에서 ConveniencePayService라는 이름의 빈(Bean)을 가져옴
        // ConveniencePayService는 결제와 결제 취소를 처리하는 서비스 클래스
        ConveniencePayService conveniencePayService =
            applicationContext.getBean("conveniencePayService",
                ConveniencePayService.class);

        // *** 결제 요청 (PayRequest) ***
        // 편의점 G25에서 1000원을 카드(PayMethodType.CARD)로 결제
        // ConvenienceType.G25: 편의점 타입 (예: G25, CU 등)
        // amount: 결제 금액
        PayRequest payRequest = new PayRequest(PayMethodType.CARD,
            ConvenienceType.G25, 1000);

        // pay 메서드를 호출하여 결제를 처리
        // ConveniencePayService의 pay 메서드는 결제 요청을 받아 결과를 반환
        PayResponse payResponse = conveniencePayService.pay(payRequest);

        // 결제 결과를 콘솔에 출력
        System.out.println(payResponse);

        // *** 결제 취소 요청 (PayCancelRequest) ***
        // 편의점 G25에서 500원을 현금(PayMethodType.MONEY)으로 취소
        // ConvenienceType.G25: 취소 대상 편의점
        // amount: 취소 금액
        PayCancelRequest payCancelRequest = new PayCancelRequest(PayMethodType.MONEY,
            ConvenienceType.G25, 500);

        // payCancel 메서드를 호출하여 결제 취소를 처리
        // ConveniencePayService의 payCancel 메서드는 결제 취소 요청을 받아 결과를 반환
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);

        // 결제 취소 결과를 콘솔에 출력
        System.out.println(payCancelResponse);
    }
}
