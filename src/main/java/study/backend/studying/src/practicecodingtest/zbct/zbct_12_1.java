package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_12_1 {
    public int solution(int[] numbers) {
        // 문제 제한사항에 따라 0부터 100000까지 체크 가능한 배열 생성
        // 각 숫자의 존재 여부를 표시할 boolean 배열
        // false: 숫자가 없음, true: 숫자가 있음
        boolean[] isExist = new boolean[100001];

        // 연속된 수의 시작점(최솟값)을 저장할 변수
        // 최댓값으로 초기화 (더 작은 값이 나오면 교체하기 위해)
        int min = 100001;

        // 배열을 한 번 순회하면서 두 가지 작업 수행
        for(int num : numbers) {
            // 1. 현재 숫자가 존재한다고 표시
            isExist[num] = true;

            // 2. 양수들 중에서 최솟값 찾기
            // 이 최솟값부터 연속된 수가 시작됨
            if(num > 0) {
                min = Math.min(min, num);
            }
        }

        // 찾은 최솟값부터 차례대로 확인
        // 연속된 수들 중 처음으로 없는 수를 찾음
        for(int i = min; i <= 100000; i++) {
            // isExist[i]가 false라는 것은
            // i라는 숫자가 배열에 없다는 의미
            if(!isExist[i]) {
                return i;  // 첫 번째로 없는 수를 반환
            }
        }

        // 여기까지 왔다는 것은 모든 연속된 수가 있다는 의미
        // 다음 수를 반환
        return min;
    }
}