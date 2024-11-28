package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayList;
import java.util.List;

public class zbct_13_5 {
    public int[][] solution(int[][] buildings) {
        int maxRight = 0; // x축의 가장 오른쪽 끝 좌표를 찾기 위한 변수

        // 1. 모든 건물의 오른쪽 끝 좌표 중 가장 큰 값을 찾음
        for (int[] building : buildings) {
            maxRight = Math.max(maxRight, building[1]); // 오른쪽 좌표를 최대값으로 갱신
        }

        // 2. x축 높이를 저장할 배열 생성 (0부터 maxRight까지)
        int[] heights = new int[maxRight + 1];

        // 3. 각 건물의 높이를 해당 x축 구간에 반영
        for (int[] building : buildings) {
            int left = building[0]; // 건물의 왼쪽 x좌표
            int right = building[1]; // 건물의 오른쪽 x좌표
            int height = building[2]; // 건물의 높이
            for (int i = left; i < right; i++) { // x축 구간 반복
                heights[i] = Math.max(heights[i], height); // 현재 높이와 건물 높이 중 더 큰 값으로 갱신
            }
        }

        // 4. 결과를 저장할 리스트
        List<int[]> result = new ArrayList<>();
        int prevHeight = 0; // 이전 높이를 기록하기 위한 변수

        // 5. 키포인트를 추출 (높이가 변하는 지점)
        for (int x = 0; x <= maxRight; x++) {
            if (heights[x] != prevHeight) { // 높이가 변한 경우
                result.add(new int[]{x, heights[x]}); // 키포인트 추가
                prevHeight = heights[x]; // 이전 높이 갱신
            }
        }

        // 6. 결과를 2차원 배열로 변환하여 반환
        return result.toArray(new int[result.size()][]);
    }
}
