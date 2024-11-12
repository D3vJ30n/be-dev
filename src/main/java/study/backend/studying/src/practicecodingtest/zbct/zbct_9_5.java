package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_9_5 {
    public int solution(String[] array, String s) {
        // 조건을 만족하는 문자열의 개수를 저장할 변수
        int answer = 0;

        // array 배열의 각 문자열을 순회
        // for-each 문을 사용하여 array의 각 요소를 str 변수에 할당
        for (String str : array) {
            // s.contains(str)은 s 문자열 안에 str이 포함되어 있는지 확인
            // 예: s = "naver", str = "nav" -> true 반환
            //     s = "naver", str = "abc" -> false 반환
            // 하지만 이 코드도 틀렸습니다!
            // contains()는 문자열이 포함만 되어있는지 확인하고
            // 우리가 원하는 '접두사' 여부는 확인하지 못합니다
            if (s.contains(str)) {
                // 조건이 참이면 카운트 증가
                answer++;
            }
        }

        // 최종 개수 반환
        return answer;
    }
}