package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Hashtable;

public class zbct_hint_2_1 {
    public String solution(String s) {
        // 입력값 유효성 검사
        // null이거나 빈 문자열이면 빈 문자열 반환
        if (s == null || s.isEmpty()) {
            return "";
        }
        // 문자의 등장 횟수를 저장할 HashTable 생성
        // key: 문자, value: 등장 횟수
        Hashtable<Character, Integer> table = new Hashtable<>();

        // 첫 번째 순회: 각 문자의 등장 횟수 계산
        // merge 메서드 사용: 키가 없으면 1로 초기화, 있으면 기존 값에 1을 더함
        // Integer::sum은 (기존값, 새값) -> 기존값 + 새값과 동일
        for (char c : s.toCharArray()) {
            table.merge(c, 1, Integer::sum);
        }
        // 결과 문자열을 만들기 위한 StringBuilder
        // String + 연산자 대신 StringBuilder 사용 (성능 향상)
        StringBuilder sb = new StringBuilder();

        // 두 번째 순회: 알파벳 순서대로 확인 (자동 정렬 효과)
        // 'a'부터 'z'까지 순회하면서 한 번만 등장한 문자 찾기
        for (char c = 'a'; c <= 'z'; c++) {
            // 현재 문자가 HashTable에 있고, 등장 횟수가 1인 경우
            if (table.containsKey(c) && table.get(c) == 1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}