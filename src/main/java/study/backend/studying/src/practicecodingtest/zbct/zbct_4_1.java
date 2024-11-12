package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_4_1 {
    public String solution(int[] arr) {
        // StringBuilder: 문자열을 효율적으로 연결하기 위한 클래스
        StringBuilder sb = new StringBuilder();

        // 배열의 모든 요소를 순회
        for (int i = 0; i < arr.length; i++) {
            // arr[i]의 int값을 char로 형변환하여 문자로 변환
            // (char)로 캐스팅하면 ASCII 코드값에 해당하는 문자로 변환됨(캐스팅 == 타입변환)
            sb.append((char) arr[i]); // append(): 문자 추가
        }
        // StringBuilder를 String으로 변환하여 반환
        return sb.toString();
    }
}