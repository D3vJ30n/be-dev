package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_13_2_me {
    public int[] solution(String[] words, String[] queries) {
        // 결과를 저장할 정수 배열 생성
        int[] result = new int[queries.length];

        // queries 배열 순회
        for (int i = 0; i < queries.length; i++) {
            // 현재 질의
            String query = queries[i];
            // 현재 질의에 매칭되는 단어의 개수를 저장할 변수
            int count = 0;

            // 앞부분이 *이면 접미사 검색
            if (query.startsWith("*")) {
                // '*'를 제외한 나머지 부분을 접미사로 추출
                String suffix = query.substring(1);
                // 모든 단어를 순회하며 조건 확인
                for (String word : words) {
                    // 단어가 해당 접미사로 끝나면
                    if (word.endsWith(suffix)) {
                        // 카운트를 증가
                        count++;
                    }
                }
            } else if (query.endsWith("*")) {
                // '*'로 끝나면 접두사 검색
                // '*'를 제외한 나머지 부분을 접두사로 추출
                String prefix = query.substring(0, query.length() - 1);
                // 모든 단어를 순회하며 조건 확인
                for (String word : words) {
                    // 단어가 해당 접두사로 시작하면
                    if (word.startsWith(prefix)) {
                        // 카운트를 증가
                        count++;
                    }
                }
            } else {
                // '*'가 없으면 정확히 일치하는 단어 찾기
                // 모든 단어를 순회하며 조건 확인
                for (String word : words) {
                    // 단어가 질의와 완전히 동일하면
                    if (word.equals(query)) {
                        // 카운트를 증가
                        count++;
                    }
                }
            }

            // 현재 질의에 대한 결과를 result 배열에 저장
            result[i] = count;
        }

        // 결과 배열을 반환
        return result;
    }
}
