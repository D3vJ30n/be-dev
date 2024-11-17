package study.backend.studying.src.practicecodingtest.zbct;

import java.util.HashMap;
import java.util.Map;

public class zbct_7_4 {
    public int solution(int[] arr) {
        int answer = 0;

        HashMap<Integer, Integer> countMap = new HashMap<>();

        // for 배열의 각 원소n: countMap에 n의 등장 횟수 증가
        for (int n : arr) {
            countMap.put(n, countMap.getOrDefault(n, 0) + 1);
        }

        // 숫자 카운트 확인
        // for countMap의 각 entry
        // if 해당 숫자의 등장 횟수가 1
        // return 해당 숫자와 쌍을 이루는 다른 숫자
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                answer = entry.getKey();
                break;
            }
        }

        // else if 모든 숫자가 2번씩 등장
        // return 0
        if (answer == 0) {
            return 0;
        }

        return answer;
    }
}