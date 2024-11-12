package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Arrays;
import java.util.stream.Collectors;

public class zbct_4_5 {
    public String solution(String[] arr) {
        // String.join(구분자, 배열)
        // - 구분자: 문자열 사이에 들어갈 문자 (",")
        // - 배열: 합칠 문자열들 (arr)
        // 예: arr=["A","B","C"] -> "A,B,C"
        String result = String.join(",", arr);

        // 합쳐진 문자열 반환
        return result;
    }
}