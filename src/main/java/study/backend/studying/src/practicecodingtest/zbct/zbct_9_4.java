package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_9_4 {
    public int solution(int[] nums) {
        // 동일한 값을 가진 쌍의 개수를 저장할 변수
        int answer = 0;

        // 첫 번째 숫자를 선택하기 위한 반복문 (i = a)
        // i는 배열의 처음부터 마지막 전까지 순회
        // (마지막 원소는 j가 순회할 수 없으므로 length-1까지만)
        for (int i = 0; i < nums.length; i++) {
            // 두 번째 숫자를 선택하기 위한 반복문 (j = b)
            // j는 i+1부터 시작 (0 <= a < b 조건 만족을 위해)
            // j는 배열의 끝까지 순회
            for (int j = i + 1; j < nums.length; j++) {
                // nums[a] == nums[b] 조건 검사
                // i번째 원소와 j번째 원소가 같은지 확인
                if (nums[i] == nums[j]) {
                    // 조건을 만족하는 쌍을 발견할 때마다 카운트 증가
                    answer++;
                }
            }
        }

        // 발견된 총 쌍의 개수 반환
        return answer;
    }
}

/*
문제 해석 및 설명:
1. 목적: 배열에서 같은 값을 가진 두 원소의 쌍의 개수를 찾기

2. 제약 조건:
  - 0 <= a < b < nums.length (인덱스 a가 b보다 작아야 함)
  - nums[a] == nums[b] (두 원소의 값이 같아야 함)

3. 알고리즘 설명:
  - 이중 반복문을 사용하여 모든 가능한 쌍을 검사
  - 첫 번째 반복문(i)은 첫 번째 원소 선택
  - 두 번째 반복문(j)은 i 이후의 원소들을 선택
  - i < j 조건이 자동으로 만족됨 (j는 i+1부터 시작)

4. 시간 복잡도: O(n²)
  - n은 nums 배열의 길이
  - 모든 가능한 쌍을 검사해야 하므로 이중 반복문 필요

5. 사용 예시:
nums = [1, 2, 1, 3, 1]의 경우:
- (0,2) 쌍: nums[0] == nums[2] == 1
- (0,4) 쌍: nums[0] == nums[4] == 1
- (2,4) 쌍: nums[2] == nums[4] == 1
따라서 answer = 3
*/