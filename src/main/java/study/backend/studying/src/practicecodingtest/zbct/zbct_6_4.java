package study.backend.studying.src.practicecodingtest.zbct;

import java.util.HashSet;
import java.util.Set;

public class zbct_6_4 {
    /**
     * 주어진 문자열에서 중복을 제외한 단어의 개수를 반환하는 메서드
     * 대소문자 구분 없이 같은 단어로 취급
     * 예: "Hello world Nice world" -> 3 ("world" 중복)
     */
    public int solution(String s) {
        // 1. 입력 문자열을 StringBuilder로 변환
        // 문자열 처리를 위한 가변적인 문자열 객체 생성
        StringBuilder sb = new StringBuilder(s);

        // 2. 문자열을 공백 기준으로 단어 분리
        // 예: "Hello world Nice world" -> ["Hello", "world", "Nice", "world"]
        String[] words = sb.toString().split(" ");

        // 3. 중복된 단어를 제거하기 위한 HashSet 생성
        // HashSet은 중복을 자동으로 제거하는 자료구조
        Set<String> set = new HashSet<>();

        // 4. 각 단어를 소문자로 변환하여 Set에 추가
        // 대소문자 구분 없이 처리하기 위해 toLowerCase() 사용
        // 예: "Hello" -> "hello", "World" -> "world"
        for(String word : words) {
            set.add(word.toLowerCase());
        }

        // 5. Set의 크기 반환 (중복이 제거된 단어의 수)
        // 예: {"hello", "world", "nice"} -> 3
        return set.size();
    }
}