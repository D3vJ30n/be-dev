package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_12_2 {
    public int[] solution(int[] a, int[] b) {
        // 1. 일단 결과를 저장할 배열을 만들어요 (길이는 더 긴 배열보다 1 크게)
        int[] answer = new int[Math.max(a.length, b.length) + 1];

        // 2. 뒤에서부터 더할거예요 (왜냐면 우리가 손으로 계산할 때도 뒤에서부터 하잖아요!)
        int i = a.length - 1;    // a 배열의 마지막 위치
        int j = b.length - 1;    // b 배열의 마지막 위치
        int k = answer.length - 1;    // 결과 배열의 마지막 위치
        int carry = 0;    // 받아올림 수. 일단 0으로 시작

        // 3. 뒤에서부터 더하기
        while (i >= 0 || j >= 0) {
            int sum = carry;    // 일단 전에 받아올린 수를 더해요

            // a 배열에 숫자가 있으면 더해요
            if (i >= 0) {
                sum += a[i];
                i--; // 다음 숫자로 이동
            }

            // b 배열에 숫자가 있으면 더해요
            if (j >= 0) {
                sum += b[j];
                j--; // 다음 숫자로 이동
            }

            // 받아올림 계산
            carry = sum / 10;
            // 현재 자리에는 나머지만 넣어요
            answer[k] = sum % 10; // 현재 자리에 넣어요
            k--; // 다음 자리로 이동
        }

        // 4. 마지막 받아올림이 있으면 처리
        if (carry > 0) { // 마지막에 받아올림이 있으면
            answer[k] = carry; // 첫 번째 자리에 받아올림을 넣어요
            return answer;
        }

        // 5. 받아올림이 없으면 첫 번째 자리를 제외한 배열을 반환
        int[] result = new int[answer.length - 1]; // 첫 번째 자리를 제외한 배열을 만들어요
        System.arraycopy(answer, 1, result, 0, result.length); // 첫 번째 자리를 제외하고 복사해요
        return result;
    }
}