package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_3_4 {
    public int solution(String s) {
        int result = 0;
        int num = 0;
        int sign = 1;  // + 는 1, -는 -1

        // 첫 문자가 -면 부호부터 바꿈
        if(s.charAt(0) == '-') { // charAt(0)은 0번 위치(첫번째)의 문자를 가져옴, char + at
            sign = -1;
            s = s.substring(1); // 첫 글자가 -일 때 이미 sign = -1로 이미 정했으니 부호빼고 숫자부터 시작하는 문자열로 만듦, 앞에서 한글자 자르기
        }
        // 한글자씩 때려박기
        for(char c : s.toCharArray()) {
            // 숫자면 계속 만들기
            if(c >= '0' && c <= '9') { // 현재 문자(c)가 숫자인지 검사 -> 문자,숫자 모두 입력 들어올 수 있다고 생각, 예: c가 '5'면 -> '0' <= '5' <= '9' 이므로 true
                num = num * 10 + (c - '0'); // 기존 숫자(num)에 새 숫자를 더하는 과정
                continue;
            }
            // 부호 나오면 이전까지 계산
            result += sign * num; // 더하기면: 1 * 숫자 = 그대로, 빼기면: -1 * 숫자 = 음수로

            // 부호 바꾸고 숫자 초기화
            sign = (c == '+') ? 1 : -1; // c가 +면 sign 1에 저장, -면 sign -1에 저장
            num = 0; // 다음 숫자를 위해 초기화
        }
        // 마지막 숫자 처리
        result += sign * num;

        return result;
    }
}