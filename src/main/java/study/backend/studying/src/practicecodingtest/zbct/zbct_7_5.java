package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_7_5 {
    public int solution(int num) {
        // 제약조건 체크
        if (num >= 100000 || num <= -100000) {
            return 0;
        }

        // 절대값 변환
        int absNum = Math.abs(num);
        // 문자열로 변환
        StringBuilder sb = new StringBuilder(String.valueOf(absNum));
        // 뒤집기
        sb.reverse();
        // 뒤집은 문자열을 정수로 변환
        int reversed = Integer.parseInt(sb.toString());
        // 원래 수가 음수면 -부호 붙이기
        return num < 0 ? -reversed : reversed;
    }
}