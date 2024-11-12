package study.backend.studying.src.practicecodingtest.zbct;



public class zbct_5_3 {
    public String solution(String s) {
        // StringBuilder 객체 생성
        // 입력받은 문자열 s를 가지고 StringBuilder 초기화
        // StringBuilder는 문자열 조작에 효율적인 클래스
        StringBuilder sb = new StringBuilder(s);

        // 문자열 뒤집기 및 반환
        // reverse(): 문자열을 뒤집는 메소드
        // toString(): StringBuilder를 String으로 변환
        return sb.reverse().toString();
    }
}
