package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_2_5 {
    public int solution(int[] arr) {
        // 첫 번째 값으로 초기화
        int balance = arr[0];

        // 배열의 모든 값을 확인
        for(int i = 0; i < arr.length; i++) {
            // 현재 값이 0과 더 가깝거나
            // 같은 거리일 때 더 작은 값 선택
            if(Math.abs(arr[i]) < Math.abs(balance) ||
                (Math.abs(arr[i]) == Math.abs(balance) && arr[i] < balance)) {
                balance = arr[i];
            }
        }
        // 0과 가장 가까운 값 반환
        return balance;
    }
}