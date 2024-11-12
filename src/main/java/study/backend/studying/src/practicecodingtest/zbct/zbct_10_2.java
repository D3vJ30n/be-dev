package study.backend.studying.src.practicecodingtest.zbct;

/*
[문제 해석]
1. 정수 배열 nums에서 정수 n이 처음 등장하는 인덱스를 찾는 문제
2. nums에 n이 없으면 -1 반환
3. 인덱스는 0부터 시작
4. 여러 번 등장하면 가장 작은 인덱스 반환

[의사코드]
1. 배열을 처음부터 순회
2. n과 같은 값을 찾으면 해당 인덱스 반환
3. 끝까지 못찾으면 -1 반환
*/

class zbct_10_2 {
    public int solution(int[] nums, int n) {
        // 1. 배열 전체 순회
        for (int i = 0; i < nums.length; i++) {
            // 2. n과 같은 값을 찾으면 해당 인덱스 반환
            if (nums[i] == n) {
                return i;  // 현재 인덱스 반환
            }
        }
        // 3. 끝까지 못찾으면 -1 반환
        return -1;
    }
}

/*
[입출력 예시]
입력: nums = [1, 3, 5, 3, 2], n = 3
처리과정:
1. index 0 (값 1) -> 불일치
2. index 1 (값 3) -> 일치! 1 반환
출력: 1

입력: nums = [2, 5, 8, 0], n = 3
처리과정:
1. 배열 전체를 순회했지만 3을 찾지 못함
출력: -1
*/