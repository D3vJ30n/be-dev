package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_11_1 {
    public int solution(int n) {
        // n보다 작은 소수의 개수를 구하는 메소드
        // n: 주어진 정수 (0 < n <= 100)

        // 1이하의 수는 소수가 없으므로 0 반환
        if (n <= 1) return 0;

        // 숫자의 소수 여부를 저장할 배열
        // index가 그 숫자를 의미하고, 값이 true면 소수, false면 소수가 아님
        boolean[] isPrime = new boolean[n];

        // 처음에는 모든 수를 소수라고 가정하고 true로 초기화
        // 2부터 n-1까지만 검사하면 되므로 이 범위만 초기화
        for (int i = 2; i < n; i++) { // 0,1은 소수가 아니므로 2부터 시작
            isPrime[i] = true;
        }

        // 에라토스테네스의 체 알고리즘 적용
        // i * i < n: i의 제곱까지만 검사하면 됨
        // 예: n=15일 때 4까지만 검사해도 15 미만의 모든 합성수를 찾을 수 있음, 16부터는 이미 처리됨
        for (int i = 2; i * i < n; i++) { // i: 2부터 n의 제곱근까지, i의 배수를 모두 false로 변경
            // i가 소수일 때
            if (isPrime[i]) {
                // i의 배수들을 모두 소수가 아니라고 표시
                // j는 i*i부터 시작 (그 이전의 배수는 이미 처리됨)
                // 예: i=2일 때 4,6,8,...를 false로 변경
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        // 소수의 개수를 세는 부분
        int count = 0;  // 소수의 개수를 저장할 변수

        // 2부터 n-1까지의 숫자 중 소수의 개수를 카운트
        for (int i = 2; i < n; i++) { // 1은 소수가 아니므로 2부터 시작
            if (isPrime[i]) count++;  // isPrime[i]가 true이면 i는 소수
        }

        // n보다 작은 소수의 총 개수 반환
        return count;

       /* 예시: n = 15일 때
          소수: 2,3,5,7,11,13
          반환값: 6

          작동과정:
          1. 2~14까지 true로 초기화
          2. 2의 배수(4,6,8,10,12,14) 제거
          3. 3의 배수(9) 제거 (6,12는 이미 제거됨)
          4. 4는 이미 false라서 건너뜀
          5. 남은 true값들(2,3,5,7,11,13)의 개수인 6 반환
       */
    }
}