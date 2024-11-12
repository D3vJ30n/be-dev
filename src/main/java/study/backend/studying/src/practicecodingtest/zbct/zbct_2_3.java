package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_2_3 {
    // 두 수의 최대공약수를 구하는 메서드
    public int solution(int a, int b) {
        if (b == 0) return a;
        return solution(b, a % b);
    }

    // 배열의 모든 수의 최대공약수를 구하는 메서드
    public int solution(int[] A) {
        int result = A[0];

        // 배열의 모든 수에 대해 최대공약수 계산
        for(int i = 1; i < A.length; i++) {
            result = solution(result, A[i]);
        }

        return result;
    }
}