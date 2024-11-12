package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_11_4 {
    public int solution(int n, int i, int j) {
        // n: 배열의 크기 (2의 제곱수: 2,4,8,16...)
        // i: 찾고자 하는 위치의 행 번호 (0부터 시작)
        // j: 찾고자 하는 위치의 열 번호 (0부터 시작)

        // 2x2 크기일 때는 기본 패턴 반환
        // ※ 2x2 기본 패턴:
        // 2 1
        // 3 4
        if (n == 2) {
            if (i == 0 && j == 1) return 1;  // (0,1) 우상단
            if (i == 0 && j == 0) return 2;  // (0,0) 좌상단
            if (i == 1 && j == 0) return 3;  // (1,0) 좌하단
            return 4;                         // (1,1) 우하단
        }

        // n을 2로 나눠서 4등분된 사분면의 크기를 구함
        int half = n/2;
        // 각 사분면의 시작 번호를 저장할 변수
        int baseNum = 0;

        // 현재 위치(i,j)가 어느 사분면에 있는지 확인
        // half를 기준으로 상하좌우 판단
        if (i < half) {  // 상단 영역
            if (j >= half) {
                // 우상단(1사분면)
                // baseNum은 0으로 시작 (첫 번째 영역이므로)
                baseNum = 0;
                j -= half;  // 다음 재귀를 위해 j값 조정
            } else {
                // 좌상단(2사분면)
                // baseNum은 (half*half)부터 시작
                // 예: n=4일때 half=2이므로 4개의 숫자를 건너뜀
                baseNum = half * half;
            }
        } else {  // 하단 영역
            if (j < half) {
                // 좌하단(3사분면)
                // baseNum은 (2*half*half)부터 시작
                // 예: n=4일때 half=2이므로 8개의 숫자를 건너뜀
                baseNum = 2 * half * half;
                i -= half;  // 다음 재귀를 위해 i값 조정
            } else {
                // 우하단(4사분면)
                // baseNum은 (3*half*half)부터 시작
                // 예: n=4일때 half=2이므로 12개의 숫자를 건너뜀
                baseNum = 3 * half * half;
                i -= half;  // 다음 재귀를 위해 i,j값 조정
                j -= half;
            }
        }

        // 현재 사분면의 시작번호(baseNum)에
        // 더 작은 크기에서 재귀적으로 구한 위치의 값을 더해서 반환
        return baseNum + solution(half, i, j);
    }
}