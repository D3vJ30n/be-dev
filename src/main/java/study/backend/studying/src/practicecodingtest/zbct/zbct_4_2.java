package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_4_2 {
    public int solution(String[] arr) {
        // 첫 번째 이진수를 10진수로 변환하여 초기값 설정
        int result = Integer.parseInt(arr[0], 2);

        // 두 번째 이진수부터 순차적으로 XOR 연산
        for(int i = 1; i < arr.length; i++) {
            // 현재 이진수를 10진수로 변환하여 XOR
            int current = Integer.parseInt(arr[i], 2);
            result = result ^ current;
        }
        return result;
    }
}