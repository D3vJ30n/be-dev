package study.backend.studying.src.practicecodingtest.zbct;

import java.util.HashMap;

public class zbct_6_3 {
    /**
     * 패턴 문자열과 입력 문자열이 일치하는지 확인하는 메서드
     * '가'는 아무 문자나 될 수 있지만, 같은 위치의 '가'는 같은 문자여야 함
     * 예시: p="가나다", s="철나다" -> true ('가'가 '철'로 대체)
     */
    public boolean solution(String p, String s) {
        // 1. 공백을 기준으로 문자열을 단어 배열로 분리
        // 예: "가나 다라" -> ["가나", "다라"]
        String[] pArr = p.split(" ");
        String[] sArr = s.split(" ");

        // 2. 패턴과 문자열의 단어 개수가 다르면 실패
        // 예: p="가나 다라", s="가나" -> false
        if (pArr.length != sArr.length) return false;

        // 3. 각 단어별로 패턴 일치 여부 확인
        for (int i = 0; i < pArr.length; i++) {
            String word_p = pArr[i];  // 패턴의 현재 단어
            String word_s = sArr[i];  // 비교할 문자열의 현재 단어

            // 4. 단어의 길이가 다르면 실패
            // 예: "가나" vs "가나다" -> false
            if (word_p.length() != word_s.length()) return false;

            // 5. '가' 문자와 매핑되는 문자를 저장할 HashMap
            // key: '가' 문자, value: 매핑되는 문자
            HashMap<Character, Character> map = new HashMap<>();

            // 6. 현재 단어의 각 문자를 하나씩 비교
            for (int j = 0; j < word_p.length(); j++) {
                char pChar = word_p.charAt(j);  // 패턴의 현재 문자
                char sChar = word_s.charAt(j);  // 비교할 문자열의 현재 문자

                // 7. 패턴 문자가 '가'인 경우
                if (pChar == '가') {
                    // 7-1. '가'가 처음 등장하는 경우, 현재 문자와 매핑
                    // 예: "가나" vs "철나" -> '가'와 '철' 매핑
                    if (!map.containsKey(pChar)) {
                        map.put(pChar, sChar);
                    }
                    // 7-2. '가'가 이미 다른 문자와 매핑된 경우, 이전 매핑과 같은지 확인
                    // 예: "가가" vs "철수" -> false ('가'가 '철'과 '수'로 다르게 매핑)
                    else if (map.get(pChar) != sChar) {
                        return false;
                    }
                }
                // 8. '가'가 아닌 경우, 문자가 정확히 일치해야 함
                // 예: "나다" vs "나라" -> false ('다'와 '라'가 다름)
                else if (pChar != sChar) {
                    return false;
                }
            }
        }

        // 9. 모든 검사를 통과하면 패턴이 일치하는 것
        return true;
    }
}