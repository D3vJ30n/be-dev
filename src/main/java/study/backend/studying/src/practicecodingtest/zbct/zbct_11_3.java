package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_11_3 {
    public int solution(int N) {
        int answer = 0;
        int[] dp = new int[N + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        answer = dp[N];
        return answer;
    }
}

/*
최종 요약
+1은 인덱스를 0부터 N까지 사용할 수 있도록 하기 위해 배열의 크기를 N + 1로 설정한 것입니다.
dp[0]과 dp[1]은 각각 "높이가 0일 때의 방법"과 "높이가 1일 때의 방법"을 표현하며, 둘 다 1가지 경우만 있기 때문에 1로 설정한 것입니다.
-1과 -2는 가로와 세로 배치의 경우를 나타내며, 이를 통해 점화식 dp[i] = dp[i - 1] + dp[i - 2]를 적용하여 높이 i까지 쌓는 모든 방법의 수를 계산하는 것입니다.
 */