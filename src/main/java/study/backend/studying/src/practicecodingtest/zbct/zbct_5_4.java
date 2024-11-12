package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_5_4 {
    public int solution(int n) {
        // Math.sqrt()로 입력받은 정수의 제곱근을 계산
        // double 타입으로 반환됨 (예: sqrt(16) = 4.0)
        double sqrt = Math.sqrt(n);

        // 제곱근이 정수인지 확인
        // sqrt와 sqrt를 int로 형변환한 값이 같으면 정수
        // (예: 4.0 == 4 -> true, 4.5 == 4 -> false)
        if (sqrt == (int) sqrt) {
            // 제곱근이 정수면 그 값을 int로 형변환하여 반환
            return (int) sqrt;
        } else {
            // 제곱근이 정수가 아니면 0 반환
            return 0;
        }
    }
}