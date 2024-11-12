package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_6_2 {
    /**
     * 최대값과 최소값을 제외한 나머지 숫자들의 평균을 반환하는 메서드
     * 예) [2,3,3,3,20] -> (3+3+3)/3 = 3 반환
     *     최소값(2), 최대값(20) 제외한 나머지 수들의 평균값 구하기
     */
    public int solution(int[] arr) {
        // 1. 첫 번째 값을 기준으로 최대값, 최소값 초기화
        int min = arr[0];    // 최소값 저장
        int max = arr[0];    // 최대값 저장
        int sum = 0;         // 전체 합계 저장

        // 2. 배열을 한 번 순회하면서 동시에 세 가지 값을 구함
        for (int number : arr) {
            sum += number;              // 배열의 전체 합계 누적
            if (number < min) {         // 현재 값이 min보다 작으면
                min = number;           // min 값 갱신
            }
            if (number > max) {         // 현재 값이 max보다 크면
                max = number;           // max 값 갱신
            }
        }

        // 3. 평균값 계산 및 반환
        // - 전체 합계에서 최대값, 최소값을 뺌
        // - 전체 개수에서 2를 빼서 나눔 (최대, 최소 제외)
        // - 정수 나눗셈으로 소수점 이하는 자동으로 버림
        return (sum - max - min) / (arr.length - 2);
    }
}