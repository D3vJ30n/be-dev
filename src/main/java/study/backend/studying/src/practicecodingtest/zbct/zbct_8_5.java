package study.backend.studying.src.practicecodingtest.zbct;

import java.util.LinkedList;
import java.util.Queue;

public class zbct_8_5 {
    public int solution(int N, int K) {
        // Queue 인터페이스를 구현한 LinkedList 생성
        // LinkedList를 사용하면 원형 큐처럼 앞에서 제거하고 뒤에 추가하는 것이 효율적
        Queue<Integer> queue = new LinkedList<>();

        // 1부터 N까지의 숫자를 큐에 순서대로 추가
        // 예: N=7이면 큐에 1,2,3,4,5,6,7이 순서대로 들어감
        for (int i = 1; i <= N; i++) {
            queue.offer(i);  // offer()는 큐의 끝에 요소를 추가
        }

        // 큐에 하나의 숫자만 남을 때까지 반복
        // 마지막 남은 숫자가 정답이 됨
        while (queue.size() > 1) {  // 큐의 크기가 1보다 클 때 계속 반복
            // K-1번째까지의 숫자는 큐의 뒤로 다시 넣음
            // 예: K=3일 때, 1,2번째 숫자는 뒤로 보내고 3번째 숫자는 제거
            for (int i = 0; i < K - 1; i++) {
                queue.offer(queue.poll());  // poll()로 앞에서 제거하고 offer()로 뒤에 추가
            }

            // K번째 숫자는 제거만 하고 다시 넣지 않음
            queue.poll();  // poll()은 큐의 첫 번째 요소를 제거하고 반환
        }

        // 마지막으로 남은 하나의 숫자가 정답
        // poll()로 마지막 남은 숫자를 꺼내어 반환
        return queue.poll();
    }
}

/*
실행 예시 (N=7, K=3):

초기 상태: [1,2,3,4,5,6,7]
1) 1,2는 뒤로 이동, 3 제거
   [4,5,6,7,1,2]
2) 4,5는 뒤로 이동, 6 제거
   [7,1,2,4,5]
3) 7,1은 뒤로 이동, 2 제거
   [4,5,7,1]
4) 4,5는 뒤로 이동, 7 제거
   [1,4,5]
5) 1,4는 뒤로 이동, 5 제거
   [4,1]
6) 4는 뒤로 이동, 1 제거
   [4]
결과: 4

시간 복잡도: O(N*K)
- 외부 while 루프: O(N) (매 반복마다 하나씩 제거)
- 내부 for 루프: O(K) (K-1번 반복)

공간 복잡도: O(N)
- N개의 숫자를 저장하는 큐 필요
*/