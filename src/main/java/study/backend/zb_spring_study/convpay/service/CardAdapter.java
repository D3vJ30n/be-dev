package study.backend.zb_spring_study.convpay.service;

import study.backend.zb_spring_study.convpay.type.*;

/**
 * 카드 결제를 처리하는 어댑터 클래스
 * PaymentInterface를 구현하여 표준화된 결제 인터페이스 제공
 */
public class CardAdapter implements PaymentInterface {
    /**
     * 카드 결제 전 인증 절차 수행
     * 실제 구현에서는 카드사 인증 로직이 들어갈 부분
     */
    public void authorization() {
        System.out.println("authorization success.");
    }

    /**
     * 카드 결제 승인 절차 수행
     * 실제 구현에서는 카드사 승인 로직이 들어갈 부분
     */
    public void approval() {
        System.out.println("approval success.");
    }

    /**
     * 카드 결제 매입 처리
     * @param payAmount 결제 금액
     * @return 매입 처리 결과 (100원 초과 시 실패)
     */
    public CardUseResult capture(Integer payAmount) {
        if(payAmount > 100) {
            return CardUseResult.USE_FAIL;
        }
        return CardUseResult.USE_SUCCESS;
    }

    /**
     * 카드 결제 매입 취소 처리
     * @param cancelAmount 취소 금액
     * @return 매입 취소 처리 결과 (1000원 미만 시 실패)
     */
    public CardUseCancelResult cancelCapture(Integer cancelAmount) {
        if(cancelAmount < 1000) {
            return CardUseCancelResult.USE_CANCEL_FAIL;
        }
        return CardUseCancelResult.USE_CANCEL_SUCCESS;
    }

    /**
     * 결제 수단 타입 반환
     * @return CARD 타입
     */
    @Override
    public PayMethodType getPayMethodType() {
        return PayMethodType.CARD;
    }

    /**
     * 카드 결제 프로세스 실행
     * 인증 -> 승인 -> 매입 순서로 진행
     * @param payAmount 결제 금액
     * @return 결제 처리 결과
     */
    @Override
    public PaymentResult payment(Integer payAmount) {
        authorization();
        approval();
        CardUseResult cardUseResult = capture(payAmount);

        if(cardUseResult == CardUseResult.USE_FAIL) {
            return PaymentResult.PAYMENT_FAIL;
        }
        return PaymentResult.PAYMENT_SUCCESS;
    }

    /**
     * 카드 결제 취소 프로세스 실행
     * @param cancelAmount 취소 금액
     * @return 결제 취소 처리 결과
     */
    @Override
    public CancelPaymentResult cancelPayment(Integer cancelAmount) {
        CardUseCancelResult cardUseCancelResult = cancelCapture(cancelAmount);

        if(cardUseCancelResult == CardUseCancelResult.USE_CANCEL_FAIL) {
            return CancelPaymentResult.CANCEL_PAYMENT_FAIL;
        }
        return CancelPaymentResult.CANCEL_PAYMENT_SUCCESS;
    }
}