package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayList;

public class zbct_7_2 {
    public int[] solution(int[] arr) {
        // 1. 동적으로 데이터를 저장할 ArrayList 생성
        // - 홀수가 추가될 때마다 크기가 자동으로 증가
        // - 초기 용량은 기본값 사용
        ArrayList<Integer> list = new ArrayList<>();

        // 2. 원본 배열을 순차적으로 순회
        // - 홀수인 경우 같은 숫자를 연속해서 두 번 추가
        // - 짝수인 경우 한 번만 추가
        for (int num : arr) {
            if (num % 2 == 1) {  // 홀수 체크
                // 홀수는 연속해서 두 번 추가하여 반복 효과 구현
                list.add(num);    // 첫 번째 추가
                list.add(num);    // 두 번째 추가 (반복)
            } else {
                // 짝수는 한 번만 추가
                list.add(num);
            }
        }

        // 3. 결과를 저장할 배열 생성
        // - 문제 요구사항에 따라 원본 배열과 같은 크기로 생성
        int[] result = new int[arr.length];

        // 4. ArrayList의 앞에서부터 원하는 만큼만 복사
        // - 원본 배열 크기만큼만 복사하여 불필요한 데이터 제외
        // - 홀수가 반복된 후 앞에서부터의 데이터만 사용
        for (int i = 0; i < arr.length; i++) {
            result[i] = list.get(i);  // i번째 위치의 데이터 복사
        }

        return result;  // 최종 결과 반환
    }
}

/*
실행 예시 설명:
입력 arr = {0, 2, 1, 4, 3, 0} 의 경우,

1. ArrayList 처리 과정
  - 0 추가: [0]
  - 2 추가: [0, 2]
  - 1 추가(홀수): [0, 2, 1, 1]
  - 4 추가: [0, 2, 1, 1, 4]
  - 3 추가(홀수): [0, 2, 1, 1, 4, 3, 3]
  - 0 추가: [0, 2, 1, 1, 4, 3, 3, 0]

2. 결과 배열 생성
  - 앞에서부터 6개(원본 크기)만 선택: {0, 2, 1, 1, 4, 3}

시간복잡도: O(N) - 배열을 한 번만 순회
공간복잡도: O(N) - 추가 ArrayList 사용
*/