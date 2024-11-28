package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class zbct_13_5_me {
    public int[][] solution(int[][] buildings) {
        // 결과를 저장할 리스트 (각 키포인트를 저장)
        List<int[]> result = new ArrayList<>();

        // 1. 건물의 모든 좌표와 높이 정보를 이벤트로 변환
        List<int[]> events = new ArrayList<>();
        for (int[] building : buildings) {
            int left = building[0];  // 건물의 왼쪽 x좌표
            int right = building[1]; // 건물의 오른쪽 x좌표
            int height = building[2]; // 건물의 높이

            // 시작점 이벤트 추가 (높이는 양수로 표시)
            events.add(new int[]{left, height});

            // 끝점 이벤트 추가 (높이는 음수로 표시)
            events.add(new int[]{right, -height});
        }

        // 2. 이벤트를 정렬
        // x좌표 기준 오름차순 정렬, x값이 같을 경우 높이 기준으로 정렬
        events.sort((a, b) -> {
            if (a[0] == b[0]) { // x값이 같으면
                return a[1] - b[1]; // 높이를 기준으로 정렬 (낮은 값이 먼저)
            }
            return a[0] - b[0]; // x값 기준 정렬
        });

        // 3. 우선순위 큐를 이용하여 현재 높이를 관리
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // 최대 힙 구현
        pq.offer(0); // 초기 높이 0으로 설정 (땅의 높이)
        int prevHeight = 0; // 이전 높이 초기값

        // 4. 이벤트 순회하며 키포인트 추출
        for (int[] event : events) {
            int x = event[0]; // 현재 이벤트의 x좌표
            int height = event[1]; // 현재 이벤트의 높이 (양수: 시작점, 음수: 끝점)

            if (height > 0) { // 시작점 이벤트인 경우
                pq.offer(height); // 높이 추가
            } else { // 끝점 이벤트인 경우
                pq.remove(-height); // 높이 제거 (음수로 저장된 끝점은 양수로 변환)
            }

            // 현재 높이의 최댓값 계산
            int curHeight = pq.peek();

            // 이전 높이와 현재 높이가 다르면 키포인트 추가
            if (prevHeight != curHeight) {
                result.add(new int[]{x, curHeight}); // 키포인트 저장
                prevHeight = curHeight; // 이전 높이 갱신
            }
        }

        // 5. 결과를 2차원 배열로 변환
        int[][] answer = new int[result.size()][2];
        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }

        return answer; // 최종 결과 반환
    }
}
