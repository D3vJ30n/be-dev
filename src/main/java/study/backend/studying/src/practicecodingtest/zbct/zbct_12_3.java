package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_12_3 {
    public String solution(String code) {
        String answer = code;  // 입력받은 코드 그대로 시작

        // 더 이상 숫자가 없을 때까지 반복
        while(answer.contains("{")) {
            // 가장 안쪽의 압축된 부분을 찾아서 해제
            int end = answer.indexOf("}");          // 가장 첫 번째 닫는 괄호 위치
            int start = answer.lastIndexOf("{", end);  // 그 앞에 있는 가장 가까운 여는 괄호
            int numStart = start - 1;              // 숫자 시작 위치

            // 숫자 찾기 (한 자리 이상일 수 있으므로)
            while(numStart >= 0 && Character.isDigit(answer.charAt(numStart))) { // 숫자가 아닐 때까지
                numStart--; // 숫자 앞까지 이동
            }
            numStart++; // 숫자 앞으로 한 칸 이동

            // 숫자와 내용 추출
            int num = Integer.parseInt(answer.substring(numStart, start)); // 숫자 추출
            String content = answer.substring(start + 1, end); // 내용 추출

            // 압축 해제: 내용을 숫자만큼 반복
            StringBuilder repeated = new StringBuilder();
            for(int i = 0; i < num; i++) { // 숫자만큼 반복
                repeated.append(content); // 내용 추가
            }

            // 원래 문자열에서 압축된 부분을 해제된 내용으로 교체
            answer = answer.substring(0, numStart) + // 숫자 앞까지
                repeated.toString() + // 압축 해제된 내용
                answer.substring(end + 1); // 닫는 괄호 뒤부터
        }

        return answer;
    }
}