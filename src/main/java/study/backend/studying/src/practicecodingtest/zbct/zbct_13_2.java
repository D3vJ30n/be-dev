package study.backend.studying.src.practicecodingtest.zbct;

import java.util.*; // ArrayList, List 등 유틸리티 클래스들을 사용하기 위해 import

public class zbct_13_2 { // Solution 클래스 선언
    // solution 메서드: 주어진 단어 배열(words)와 질의 배열(queries)을 통해 조건에 맞는 단어의 개수를 반환
    public int[] solution(String[] words, String[] queries) {
        int[] result = new int[queries.length];
        // 각 질의에 대한 결과(매칭된 단어 개수)를 저장할 정수 배열 생성
        // 크기는 queries 배열의 길이와 동일

        for (int i = 0; i < queries.length; i++) {
            // queries 배열의 각 질의를 하나씩 순회
            String query = queries[i];
            // 현재 처리 중인 질의
            int count = 0;
            // 현재 질의에 매칭되는 단어의 개수를 저장할 변수

            if (query.startsWith("*")) {
                // 질의가 '*'로 시작하면 접미사(suffix) 검색을 수행
                String suffix = query.substring(1);
                // '*'를 제외한 나머지 부분을 접미사로 추출
                for (String word : words) {
                    // 모든 단어를 순회하며 조건 확인
                    if (word.endsWith(suffix)) {
                        // 단어가 해당 접미사로 끝나면
                        count++;
                        // 카운트를 증가
                    }
                }
            } else if (query.endsWith("*")) {
                // 질의가 '*'로 끝나면 접두사(prefix) 검색을 수행
                String prefix = query.substring(0, query.length() - 1);
                // '*'를 제외한 나머지 부분을 접두사로 추출
                for (String word : words) {
                    // 모든 단어를 순회하며 조건 확인
                    if (word.startsWith(prefix)) {
                        // 단어가 해당 접두사로 시작하면
                        count++;
                        // 카운트를 증가
                    }
                }
            } else {
                // '*'가 없을 경우, 정확히 일치하는 단어를 찾는다
                for (String word : words) {
                    // 모든 단어를 순회하며 조건 확인
                    if (word.equals(query)) {
                        // 단어가 질의와 완전히 동일하면
                        count++;
                        // 카운트를 증가
                    }
                }
            }

            result[i] = count;
            // 현재 질의에 대한 결과(매칭된 단어의 개수)를 결과 배열에 저장
        }

        return result;
        // 최종적으로 각 질의에 대한 결과 배열을 반환
    }
}
