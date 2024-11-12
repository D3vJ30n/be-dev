package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Arrays;

public class zbct_9_2 {
    public int solution(int[] A, int K) {
        // 배열의 길이를 저장 (메모리 참조 횟수를 줄이기 위함)
        int n = A.length;

        // 생성 가능한 모든 두 자리 수의 개수는 n * (n-1)
        // n개 중 2개를 순서있게 뽑는 순열(Permutation) 공식: nP2 = n * (n-1)
        int[] twoDigits = new int[n * (n-1)];

        // twoDigits 배열의 인덱스로 사용할 변수
        // 조건을 만족하는 두 자리 수가 생성될 때마다 1씩 증가
        int idx = 0;

        // 첫 번째 자리 숫자 선택을 위한 반복문 (십의 자리)
        for (int i = 0; i < A.length; i++) {
            // 두 번째 자리 숫자 선택을 위한 반복문 (일의 자리)
            for (int j = 0; j < A.length; j++) {
                // 같은 위치의 숫자는 사용할 수 없음 (i != j)
                if (i != j) {
                    // A[i]를 십의 자리로, A[j]를 일의 자리로 하는 두 자리 수 생성
                    // ex) A[i]=1, A[j]=2 이면 12가 생성됨
                    twoDigits[idx++] = A[i] * 10 + A[j];
                }
            }
        }

        // 생성된 모든 두 자리 수를 오름차순으로 정렬
        // Arrays.sort()는 기본적으로 오름차순 정렬
        Arrays.sort(twoDigits);

        // 정렬된 배열에서 K번째로 큰 수를 반환
        // 배열의 마지막 인덱스는 length-1이고,
        // K번째로 큰 수는 뒤에서 K번째 위치에 있으므로 length-K를 인덱스로 사용
        return twoDigits[twoDigits.length - K];
    }
}