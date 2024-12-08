package study.backend.studying.src.practicecodingtest.zbct;

import java.util.*;

public class zbct_14_2 {
    public String solution(int[] numbers) {
        // 1. int형 배열 numbers를 문자열 배열로 변환합니다.
        //    이유: 숫자를 단순히 정렬하는 것이 아니라, 두 숫자를 이어붙였을 때
        //    더 큰 결과를 내는 순서를 찾기 위해 문자열 비교를 해야 하기 때문입니다.
        String[] strNumbers = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            // numbers[i]를 문자열로 변환하여 strNumbers에 저장
            strNumbers[i] = String.valueOf(numbers[i]);
        }

        // 2. 문자열 배열을 정렬합니다.
        //    단순히 숫자의 크기로 정렬하는 것이 아니라, "두 문자열을 이어붙였을 때 더 큰 수가 되는 순"으로 정렬해야 합니다.
        //    예: "3"과 "30"을 비교할 때,
        //        "330" vs "303" 중에 "330"이 더 크므로 "3"이 "30" 앞에 와야 합니다.
        //    이를 위해 Comparator를 사용, (b+a).compareTo(a+b)를 이용하여 비교 기준을 설정합니다.
        //    이렇게 하면 두 문자열 a, b를 연결했을 때 어떤 순서가 더 큰 값이 되는지 판단할 수 있습니다.
        Arrays.sort(strNumbers, (a, b) -> (b + a).compareTo(a + b));

        // 3. 정렬된 문자열을 모두 이어붙여 결과를 만듭니다.
        //    정렬된 순서대로 문자열을 합치면 가장 큰 수를 만들 수 있습니다.
        StringBuilder sb = new StringBuilder();
        for (String s : strNumbers) {
            sb.append(s);
        }

        // 4. 모든 숫자가 0인 경우, 예를 들어 [0,0,0]이면 "000"이 만들어질 것입니다.
        //    이 경우 "0" 한 개만 출력해야 합니다.
        //    startsWith("0")를 통해 첫 문자가 '0'인지 확인합니다.
        //    만약 첫 문자가 0이라면, 전부 0이라는 의미이므로 "0"을 반환합니다.
        String result = sb.toString();
        if (result.startsWith("0")) {
            return "0";
        }

        // 5. 모든 처리를 마친 후 가장 큰 수를 문자열로 반환합니다.
        return result;
    }

    // 참고:
    // main 메서드를 통해 간단히 테스트해볼 수 있습니다.
    // 예:
    // public static void main(String[] args) {
    //     zbct_14_2 instance = new zbct_14_2();
    //     int[] numbers = {3, 30, 34, 5, 9};
    //     System.out.println(instance.solution(numbers)); // "9534330" 출력
    // }
}
