package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_5_2 {
    public boolean solution(int n) {
        // 2보다 작은 수는 소수가 아니므로 false 반환
        if (n < 2) return false;

        // 2부터 제곱근까지 반복하면서 n이 나누어떨어지는 수가 있는지 확인(제곱근까지만 검사하면 모든 조합 확인 가능)
        for (int i = 2; i <= Math.sqrt(n); i++) {
            // n이 i로 나누어떨어지면 소수가 아니므로 false 반환
            if (n % i == 0) return false;
        }
        // 위 반복문을 통과하면 n은 소수이므로 true 반환
        return true;
    }
}