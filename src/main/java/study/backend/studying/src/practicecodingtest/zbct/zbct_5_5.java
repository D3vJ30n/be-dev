package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_5_5 {
    public String solution(String s) {
        // null이나 빈 문자열 체크
        if (s == null || s.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        // 문자열을 char 배열로 변환 후 한 문자씩 처리
        for(char c : s.toCharArray()) {
            // 삼항 연산자로 대소문자 변환
            // Character.isUpperCase(c) : 현재 문자가 대문자인지 확인
            // true면 -> toLowerCase로 소문자로 변환
            // false면 -> toUpperCase로 대문자로 변환
            sb.append(
                    Character.isUpperCase(c) ?
                    Character.toLowerCase(c) :
                    Character.toUpperCase(c)
            );
        }
        return sb.toString();
    }
}