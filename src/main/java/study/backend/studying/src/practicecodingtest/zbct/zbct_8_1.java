package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_8_1 {
    public String solution(String s) {
        // 최종 결과를 저장할 StringBuilder 객체 생성
        // StringBuilder는 String과 달리 수정이 가능하여 문자열 연산이 효율적
        StringBuilder result = new StringBuilder();

        // 0-9까지의 숫자가 이미 사용되었는지 체크하기 위한 boolean 배열
        // used[i] = true : i라는 숫자가 이미 사용됨
        // used[i] = false : i라는 숫자가 아직 사용되지 않음
        boolean[] used = new boolean[10];

        // 입력받은 문자열을 한 문자씩 처리
        // s.toCharArray(): 문자열을 문자 배열로 변환
        for(char c : s.toCharArray()) {
            // 문자를 숫자로 변환 (ASCII 코드 활용)
            // 예: '2' - '0' = 2 (ASCII 50 - 48 = 2)
            int num = c - '0';

            // 해당 숫자가 아직 사용되지 않았다면
            // !used[num]: used[num]이 false인 경우
            if(!used[num]) {
                // 해당 숫자를 사용했다고 표시
                used[num] = true;
                // 결과 문자열에 숫자와 공백을 추가
                // append(num): 숫자 추가
                // append(" "): 공백 추가
                result.append(num).append(" ");
            }
        }

        // 0부터 9까지 순회하면서 사용되지 않은 숫자 추가
        for(int i = 0; i < 10; i++) {
            // 해당 숫자가 사용되지 않았다면
            if(!used[i]) {
                // 결과 문자열에 숫자와 공백을 추가
                result.append(i).append(" ");
            }
        }

        // 최종 결과 반환
        // toString(): StringBuilder를 String으로 변환
        // trim(): 문자열 앞뒤의 공백 제거
        return result.toString().trim();
    }
}

/*
실행 예시 (s = "221123"):

1. 처음 상태
   - result = ""
   - used = [false, false, false, false, false, false, false, false, false, false]

2. 첫 번째 반복문 실행
   - '2' 처리: result = "2 ", used[2] = true
   - '2' 처리: 이미 사용됨(무시)
   - '1' 처리: result = "2 1 ", used[1] = true
   - '1' 처리: 이미 사용됨(무시)
   - '2' 처리: 이미 사용됨(무시)
   - '3' 처리: result = "2 1 3 ", used[3] = true

3. 두 번째 반복문 실행
   - 0 추가: result = "2 1 3 0 "
   - 4 추가: result = "2 1 3 0 4 "
   - 5 추가: result = "2 1 3 0 4 5 "
   - 6 추가: result = "2 1 3 0 4 5 6 "
   - 7 추가: result = "2 1 3 0 4 5 6 7 "
   - 8 추가: result = "2 1 3 0 4 5 6 7 8 "
   - 9 추가: result = "2 1 3 0 4 5 6 7 8 9 "

4. 최종 결과
   - trim() 적용 후: "2 1 3 0 4 5 6 7 8 9"
*/