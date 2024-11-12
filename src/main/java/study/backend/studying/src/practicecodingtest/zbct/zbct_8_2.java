package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayList;

public class zbct_8_2 {
    public int[] solution(int[] nums) {
        // 밀려난 학생들의 번호를 저장할 배열 (문제에서 최대 2명)
        int[] answer = new int[2];
        // answer 배열에 저장할 위치를 가리키는 인덱스
        int idx = 0;

        // 배열의 모든 위치를 순회하면서 검사
        for(int i = 0; i < nums.length; i++) {
            // i번째 위치에는 i+1 번호를 가진 학생이 있어야 함
            // 예: 0번 인덱스에는 1번 학생, 1번 인덱스에는 2번 학생...
            if(nums[i] != i + 1) {
                // 현재 위치의 학생이 올바른 번호가 아니라면
                // 원래 이 자리에 있어야 할 학생의 번호(i + 1)를 저장
                answer[idx++] = i + 1;
                // 2명을 찾으면 더 이상 검사할 필요 없음
                if(idx == 2) break;
            }
        }

        return answer;
    }
}

/*
실행 예시:
1) nums = {1, 2, 3, 4, 6, 6, 6}
   - 인덱스 4: 5번이 있어야 하는데 6이 있음 -> answer[0] = 5
   - 인덱스 6: 7번이 있어야 하는데 6이 있음 -> answer[1] = 7
   - 결과: [5, 7]

2) nums = {1, 3, 4, 8}
   - 인덱스 1: 2번이 있어야 하는데 3이 있음 -> answer[0] = 2
   - 결과: [2, 0]

시간복잡도: O(n) - 배열을 한 번만 순회
공간복잡도: O(1) - 크기가 2인 고정 배열만 사용
*/