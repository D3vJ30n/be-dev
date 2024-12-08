package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_14_5 {
    class Solution {
        public int solution(int N, int M, int[] fry, int[] clean) {
            // 이진 탐색을 위한 시간 범위 설정
            // 최소 시간은 0, 최대 시간은 넉넉히 200,000,000으로 설정합니다.
            // (문제에서 fry와 clean이 최댓값 100이고 M이 크다고 가정할 때)
            int left = 0;
            int right = 200_000_000;
            int result = right; // 결과를 저장할 변수 (초기엔 최댓값)

            // 이진 탐색 시작
            while (left <= right) {
                // 중간값 mid는 현재 시도해보는 시간
                int mid = left + (right - left) / 2;

                // mid 시간 안에 만들 수 있는 치킨 수 count를 계산합니다.
                long count = 0;

                // N대의 튀김기 각각에 대해, mid 시간 동안 몇 마리의 치킨을 만들 수 있는지 계산
                for (int i = 0; i < N; i++) {
                    // fry[i]: i번째 튀김기가 첫 치킨을 튀기는 데 걸리는 시간
                    // clean[i]: i번째 튀김기가 두 번째 치킨 이후부터 1마리를 추가로 만들 때 필요한 세척시간+튀김시간

                    // 먼저 mid 시간으로 한 마리라도 만들 수 있는지 확인
                    if (mid >= fry[i]) {
                        // 첫 치킨을 튀기는 데 fry[i] 시간이 걸리고, 튀긴 후 남은 시간은 mid - fry[i]
                        long remain = mid - fry[i];

                        // 추가 치킨을 생산하기 위해선 "fry[i] + clean[i]" 시간이 더 필요합니다.
                        // remain 시간으로 추가로 몇 마리를 더 만들 수 있는지 계산
                        long additional = remain / (fry[i] + clean[i]);

                        // 첫 치킨 포함 총 생산 마리 수: 1 + additional
                        long totalPerMachine = 1 + additional;

                        // 모든 기계의 생산량 합산
                        count += totalPerMachine;

                        // 이미 M마리 이상 만들 수 있다면 더 볼 필요 없음
                        if (count >= M) {
                            break;
                        }
                    }
                    // else: mid < fry[i]인 경우, 첫 치킨도 못 만드는 상태이므로 생산량은 0
                }

                // 이제 count와 M을 비교해서 mid 시간을 조정
                if (count >= M) {
                    // mid 시간 동안 M마리 이상 생산 가능하다면
                    // 더 짧은 시간으로도 가능한지 확인하기 위해 범위를 줄임
                    result = mid;        // 현재 mid는 가능한 답 후보이므로 result 갱신
                    right = mid - 1;     // 더 짧은 시간 확인
                } else {
                    // mid 시간 동안 M마리를 못 만들었다면
                    // 더 긴 시간이 필요하므로 left를 늘림
                    left = mid + 1;
                }
            }

            // 이진 탐색이 끝난 뒤, result에는 M마리를 만족하는 최소 시간이 들어있다.
            return result;
        }
    }

    // 필요하다면 main 메서드에서 테스트 가능
    // public static void main(String[] args) {
    //     zbct_14_5 outer = new zbct_14_5();
    //     zbct_14_5.Solution sol = outer.new Solution();
    //     int N = 2;
    //     int M = 20;
    //     int[] fry = {3, 6};
    //     int[] clean = {2, 1};
    //     System.out.println(sol.solution(N, M, fry, clean));
    //     // 문제의 예시에 따라 계산 시 58이 나와야 합니다.
    // }
}
