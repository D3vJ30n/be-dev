package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_5_1 {
    public String solution(String s) {
        // null 체크와 빈 문자열 체크를 동시에 수행
        // isEmpty()는 문자열 길이가 0인지 확인
        if(s == null || s.isEmpty())
            return "";

        // ASCII 문자들의 등장 횟수를 저장할 배열 선언
        // 크기가 128인 이유는 ASCII 코드가 0-127까지이기 때문
        int[] cnt = new int[128];

        // 최다 등장 문자를 저장할 변수를 첫 번째 문자로 초기화
        // charAt(0)으로 문자열의 첫 번째 문자 가져옴
        char max = s.charAt(0);

        // 문자열을 문자 배열로 변환하여 각 문자의 등장 횟수를 카운트
        // toCharArray()로 문자열을 문자 배열로 변환하여 순회
        for(char c : s.toCharArray())
            // 해당 문자의 ASCII 값을 인덱스로 사용하여 카운트 증가
            cnt[c]++;

        // 다시 문자열을 순회하며 가장 많이 등장한 문자 찾기
        for(char c : s.toCharArray())
            // 현재 문자의 등장 횟수가 최대 등장 횟수보다 크면
            if(cnt[c] > cnt[max])
                // 최대 등장 문자를 현재 문자로 업데이트
                max = c;

        // 찾은 문자를 String 타입으로 변환하여 반환
        // String.valueOf()로 char를 String으로 변환
        return String.valueOf(max);
    }
}