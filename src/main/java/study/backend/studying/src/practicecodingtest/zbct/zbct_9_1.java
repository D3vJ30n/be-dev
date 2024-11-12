package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_9_1 {
    public int solution(int N, int K) {
        int answer = 0;

        for (int i = 1; i <= N; i++) {
            String str = String.valueOf(i);
            int cnt = 0;

            for (char c : str.toCharArray()) {
                if (c == (char)(K + '0')) {
                    cnt++;
                } else {
                    if (cnt > 0) {
                        if (cnt >= 3) {
                            answer += 3;
                        } else {
                            answer += cnt;
                        }
                        cnt = 0;
                    }
                }
            }

            if (cnt > 0) {
                if (cnt >= 3) {
                    answer += 3;
                } else {
                    answer += cnt;
                }
            }
        }

        return answer;
    }
}