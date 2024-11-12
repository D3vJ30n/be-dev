package study.backend.studying.src.practicecodingtest.zbct;

/**
 * 문제: 완전 이진 트리의 높이별 노드 수 합계 계산
 * - 완전 이진 트리: 모든 레벨이 가득 찬 이진 트리
 * - 각 레벨별 노드 수: 2^(level-1)
 * - 높이 n까지의 모든 레벨 노드 수의 합을 구해야 함
 * - 결과가 큰 수일 수 있어 1,000,000,007로 나눈 나머지를 반환
 */
class zbct_10_4 {
    // MOD: 나머지 연산에 사용할 상수 값 (오버플로우 방지)
    private static final int MOD = 1_000_000_007;

    /**
     * @param n: 완전 이진 트리의 높이 (1 ≤ n ≤ 1000000)
     * @return 높이 n까지의 모든 레벨 노드 수의 합을 MOD로 나눈 나머지
     */
    public int solution(int n) {
        // 의사코드:
        // 1. 변수 초기화
        //    - sum: 각 레벨 노드 수의 합계를 저장 (오버플로우 방지를 위해 long 사용)
        //    - currentLevelNodes: 현재 레벨의 노드 수 (1부터 시작하여 매 레벨 2배씩 증가)
        long sum = 0;
        long currentLevelNodes = 1;

        // 2. 각 레벨별 노드 수 계산 및 합산
        //    - 1부터 n까지의 각 레벨에 대해 반복
        for (int i = 1; i <= n; i++) {
            // 2-1. 현재 레벨의 노드 수를 합계에 더하고 MOD 연산
            sum = (sum + currentLevelNodes) % MOD;
            // 2-2. 다음 레벨의 노드 수 계산 (현재 레벨 노드 수 * 2)
            currentLevelNodes = (currentLevelNodes * 2) % MOD;
        }

        // 3. 최종 결과 반환 (int로 형변환)
        return (int)sum;
    }
}

/*
주요 문법 설명:
1. private static final:
  - private: 클래스 내부에서만 접근 가능
  - static: 클래스 레벨에서 공유되는 상수
  - final: 값 변경 불가능한 상수

2. long 타입 사용:
  - int의 범위를 넘어설 수 있는 큰 수 계산을 위해 사용
  - currentLevelNodes: 매 레벨마다 2배씩 증가하므로 빠르게 커질 수 있음
  - sum: 모든 레벨의 노드 수 합계이므로 큰 수가 될 수 있음

3. MOD 연산 (%):
  - (a + b) % MOD = ((a % MOD) + (b % MOD)) % MOD 성질 활용
  - 각 계산 단계마다 MOD 연산을 수행하여 오버플로우 방지

시간 복잡도: O(n) - n번의 반복문 수행
공간 복잡도: O(1) - 추가 자료구조 사용하지 않음
*/