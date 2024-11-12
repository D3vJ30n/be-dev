package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_7_3 {
    public int solution(int n) {
        // Math.cbrt()를 사용하여 세제곱근 계산
        // n의 세제곱근을 구한 후 정수부분만 취함
        // 이를 통해 n보다 작거나 같은 가장 큰 세제곱수를 찾을 수 있음
        int answer = (int)Math.cbrt(n);

        // 세제곱 계산 후 반환
        return answer * answer * answer;
    }
}