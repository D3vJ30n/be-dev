package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_4_4 {
    public String solution(String s) {
        // 16진수(문자열) -> 10진수(정수)
        int decimal = Integer.parseInt(s, 16);

        // 10진수(정수) -> 2진수(문자열)
        String binary = Integer.toBinaryString(decimal);

        return binary;
    }
}