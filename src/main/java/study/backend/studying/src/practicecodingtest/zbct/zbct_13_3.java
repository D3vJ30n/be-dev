package study.backend.studying.src.practicecodingtest.zbct;

import java.util.*; // 유틸리티 클래스들을 가져온다. 여기에는 ArrayList, List 등이 포함된다.

class zbct_13_3 { // zbct_13_3 클래스 선언
    // solution 메서드: 주어진 단어 배열(words)와 질의 배열(queries)을 통해 조건에 맞는 단어들을 2차원 배열로 반환
    public String[][] solution(String[] words, String[] queries) {
        List<List<String>> result = new ArrayList<>();
        // 결과를 저장할 리스트의 리스트 생성
        // 외부 리스트는 각 질의에 대한 결과를 저장하고, 내부 리스트는 해당 질의에 맞는 단어들을 저장한다.

        for (String query : queries) {
            // queries 배열의 각 질의를 하나씩 순회
            List<String> matchedWords = new ArrayList<>();
            // 현재 질의에 매칭되는 단어들을 저장할 리스트 생성

            for (String word : words) {
                // words 배열의 각 단어를 하나씩 순회
                if (matchesQuery(word, query)) {
                    // matchesQuery 메서드를 호출하여 단어가 현재 질의에 매칭되는지 확인
                    matchedWords.add(word);
                    // 매칭되면 matchedWords 리스트에 추가
                }
            }

            result.add(matchedWords);
            // 현재 질의에 대한 매칭 결과를 전체 결과 리스트에 추가
        }

        // 2차원 배열로 변환: 최종적으로 반환할 결과 형식
        String[][] answer = new String[result.size()][];
        // result의 크기에 맞는 2차원 배열을 생성
        for (int i = 0; i < result.size(); i++) {
            // result 리스트의 각 내부 리스트를 순회
            answer[i] = result.get(i).toArray(new String[0]);
            // 내부 리스트를 String 배열로 변환하여 answer의 해당 위치에 저장
        }

        return answer;
        // 최종적으로 각 질의에 대한 결과를 2차원 배열로 반환
    }

    // matchesQuery 메서드: 단어(word)와 질의(query)가 일치하는지 확인
    private boolean matchesQuery(String word, String query) {
        if (word.length() != query.length()) {
            // 단어의 길이와 질의의 길이가 다르면 일치하지 않음
            return false;
        }

        for (int i = 0; i < query.length(); i++) {
            // 질의의 각 문자를 순회
            if (query.charAt(i) != '?' && query.charAt(i) != word.charAt(i)) {
                // '?'가 아니면서 해당 위치의 문자가 일치하지 않으면
                return false;
                // 일치하지 않는 것으로 간주
            }
        }

        return true;
        // 모든 조건을 만족하면 단어와 질의가 일치하는 것으로 간주
    }
}
