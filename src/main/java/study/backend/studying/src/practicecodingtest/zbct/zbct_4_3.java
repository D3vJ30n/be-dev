package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_4_3 {
    public int solution(int n) {
        // 약수의 개수를 카운트할 변수 초기화
        int count = 0;

        for(int i = 1; i * i <= n; i++) { // i의 제곱이 n보다 작거나 같을 때까지
            // 나누어 떨어지면 약수
            if(n % i == 0) {
                count++;

                // i의 제곱이 아니면 한번 더
                if(i * i != n) {
                    count++;
                }
            }
        }
        return count;
    }
}