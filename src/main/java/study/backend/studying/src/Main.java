package study.backend.studying.src;

import java.util.Arrays;

class Main {
    public int solution(int[] numbers) {
        int answer = 0;
        // for 전체 순회 후 answer가 numbers보다 작다면 true 아니면 false 반환 그러면 answer가 정답, 그리고 answer에서 가장 작은 숫자를 반환
        for (int i = 0; i < numbers.length; i++) {
            int finalI = i;

            // 배열에서 비어있는 숫자 중 가장 작은 숫자를 출력
            if (Arrays.stream(numbers).noneMatch(x -> x == finalI)) {
                answer = finalI;
                break;
            }
        }
        return answer;
    }
}