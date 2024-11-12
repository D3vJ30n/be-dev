package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_2_1 {
    public String solution(int n, String s, int t) {
        // 변수 선언
        // cycleLength: 전체 사이클의 길이 (전광판 크기 + 문자열 길이)
        // 예: n=5, s="Hello"면 cycleLength = 5 + 5 = 10
        int cycleLength = n + s.length();                  // n + 문자열 길이

        // currentPosition: 현재 시간 t에서의 문자열 위치
        // t를 cycleLength로 나눈 나머지를 구함으로써 반복되는 패턴 처리
        // 예: t=23, cycleLength=10이면 currentPosition = 3
        int currentPosition = t % cycleLength;             // 시간 % (n + 문자열 길이)

        // 전광판에 표시할 문자열을 만들기 위한 StringBuilder 객체
        StringBuilder answer = new StringBuilder();         // 결과 저장할 변수 생성 -> StringBuilder answer = new StringBuilder();

        // 전광판의 초기 상태를 '.'으로 채움
        // 예: n=5면 "....."로 초기화
        for (int i = 0; i < n; i++) {                     // n만큼 반복
            answer.append('.');                            // '.'을 정답 변수에 추가
        }

        // 입력받은 문자열 s의 각 문자를 전광판에 배치
        for (int i = 0; i < s.length(); i++) {            // 문자열 길이만큼 반복
            // pos: 각 문자가 전광판에 표시될 위치
            // n - currentPosition: 전광판 크기에서 현재 위치를 뺀 값
            // + i: 문자열의 각 문자 위치를 더함
            int pos = n - currentPosition + i;             // 위치 = n - (시간 % (n + 문자열 길이)) + i

            // 계산된 위치가 전광판 범위 내에 있는 경우에만 문자 표시
            // pos >= 0: 전광판의 시작 범위 체크
            // pos < n: 전광판의 끝 범위 체크
            if (pos >= 0 && pos < n) {                    // 위치가 범위 안에 있으면
                answer.setCharAt(pos, s.charAt(i));        // 해당 위치에 문자 저장
            }
        }

        // 최종적으로 만들어진 전광판 상태를 문자열로 변환하여 반환
        return answer.toString();                          // 문자열로 변환해서 반환
    }
}