package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Arrays;

public class zbct_6_1 {
    public int solution(int[] arr) {
        // 배열 정렬: [6,2,12,8,5,9] -> [2,5,6,8,9,12]
        Arrays.sort(arr);

        /*
        정렬된 배열에서 뒤에서부터 3개씩 확인

        예시) arr = [2,5,6,8,9,12] 인 경우
        배열 길이 = 6

        1회전) i = 5 (길이-1)
           i=5, i-1=4, i-2=3
           arr[5]=12, arr[4]=9, arr[3]=8 선택
           8 + 9 > 12 이므로 삼각형 성립!
           return 8 + 9 + 12

        만약 삼각형이 안되면 다음과 같이 진행됨:
        2회전) i = 4
           i=4, i-1=3, i-2=2
           arr[4]=9, arr[3]=8, arr[2]=6 선택

        3회전) i = 3
           i=3, i-1=2, i-2=1
           arr[3]=8, arr[2]=6, arr[1]=5 선택

        위치    :  0  1  2  3  4  5
        배열값  : [2, 5, 6, 8, 9, 12]
                    a  b  c   <- i=3일 때
        */
        for (int i = arr.length - 1; i >= 2; i--) {
            // i는 가장 큰 변의 위치
            // i-1은 중간 변의 위치
            // i-2는 가장 작은 변의 위치

            int c = arr[i];      // 현재 위치의 값 (가장 큰 변)
            int b = arr[i - 1];  // 바로 앞의 값 (중간 변)
            int a = arr[i - 2];  // 그 앞의 값 (가장 작은 변)

            // 삼각형이 되려면 작은 두 변의 합 > 가장 큰 변
            if (a + b > c) {
                return a + b + c;
            }
        }

        return 0;
    }
}

/*

 */