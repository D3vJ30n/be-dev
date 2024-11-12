package study.backend.studying.src.practicecodingtest.zbct;

/*
[문제 해석]
1. sentence에서 word가 몇 번째로 등장하는지 찾는 문제
2. 첫 번째 단어는 0번째 위치
3. word가 sentence에 없다면 -1 반환
4. 단어는 공백으로 구분됨

[의사코드]
1. sentence와 word의 유효성 검사 (null, 빈 문자열)
2. sentence 문자열 전처리 (앞뒤 공백 제거)
3. 공백을 기준으로 단어들을 분리하여 배열에 저장
4. 단어 배열을 순회하면서 찾고자 하는 word와 일치하는지 확인
5. 일치하는 단어를 찾으면 해당 인덱스 반환
6. 끝까지 못찾으면 -1 반환
*/

class zbct_10_1 {
    public int solution(String sentence, String word) {
        // 1. null 체크와 빈 문자열 체크
        // sentence.length() == 0: 문자열이 비어있는지 확인
        // null과 빈 문자열("")은 다름
        if(sentence == null || word == null ||
            sentence.length() == 0 || word.length() == 0) {
            return -1;
        }

        // 2. 문자열 전처리
        // trim(): 문자열 앞뒤의 공백 제거
        sentence = sentence.trim();
        // split("\\s+"): 하나 이상의 공백을 기준으로 문자열을 분리
        // \\s: 공백 문자를 의미, +: 1개 이상
        String[] words = sentence.split("\\s+");

        // 3. 단어 찾기
        // 배열의 처음부터 끝까지 순회하면서 탐색
        for(int i = 0; i < words.length; i++) {
            // equals(): 문자열 내용 비교 (==는 참조값 비교)
            if(words[i].equals(word)) {
                return i;  // 찾으면 즉시 해당 위치(인덱스) 반환
            }
        }

        // 4. 못 찾은 경우 -1 반환
        return -1;
    }
}

/*
[입출력 예시]
입력: sentence = "Hello every world", word = "every"
처리과정:
1. "Hello every world" -> ["Hello", "every", "world"]
2. "every"는 1번 인덱스에 위치
출력: 1

입력: sentence = "Hello every world", word = "ever"
처리과정:
1. "Hello every world" -> ["Hello", "every", "world"]
2. "ever"와 정확히 일치하는 단어가 없음
출력: -1
*/