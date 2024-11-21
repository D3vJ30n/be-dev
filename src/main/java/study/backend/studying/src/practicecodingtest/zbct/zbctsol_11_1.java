package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Arrays;

/*
문제
소수란 자기 자신과 1만을 약수로 가지는 자연수를 말한다. 예를 들어, 2, 3, 5는 소수이지만, 1, 4, 6은 소수가 아니다.

수학 영재 민준이는 n보다 작은 소수의 개수를 세 주는 프로그램을 개발하고자 한다.

n보다 작은 소수의 개수를 출력하는 프로그램을 작성하시오.

입력설명
0 < n <= 100
출력설명
n보다 작은 모든 소수의 개수를 정수로 반환

매개변수 형식
n = 15

반환값 형식
6

예시 입출력 설명
15보다 작은 소수는 다음과 같다.

2, 3, 5, 7, 11, 13 => 총 6개
 */

public class zbctsol_11_1 {
    public int solution(int n) {
        int[] intArray = new int[n];

        for (int i = 2; i < n; i++) {
            intArray[i] = 1;
        }

        for (int i = 2; i < n; i++) {
            int num = i * 2;
            while (num < n) {
                intArray[num] = 0;
                num += i;
            }
        }

        return Arrays.stream(intArray).sum();
    }
}
