package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_2_4 {
    public int solution(int[] orders, int n) {
        // 취소된 주문 개수를 세는 변수
        int count = 0;

        // 현재 확인할 주문번호 (1부터 시작)
        int current = 1;

        // orders 배열의 인덱스
        int i = 0;

        while (i < orders.length) {
            // 현재 번호가 주문 내역에 있는 경우
            if (current == orders[i]) {
                current++;
                i++;
            }
            // 현재 번호가 주문 내역에 없는 경우 (취소된 주문)
            else {
                count++;
                if (count == n) {
                    return current;
                }
                current++;
            }
        }
        return -1;
    }
}