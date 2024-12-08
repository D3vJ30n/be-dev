package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_14_4 {
    class Solution {
        public int solution(int N, int[] branches) {
            // 우리가 해야 할 일:
            // 나뭇가지들(branches)을 모두 일정한 길이 mid로 잘랐을 때,
            // 총 몇 개의 조각을 만들 수 있는지 계산하고, 그 조각 수가 N개 이상이 되는
            // "가능한 최대 mid"를 찾는 것입니다.

            // 먼저, 이진 탐색 범위를 결정합니다.
            // 자를 수 있는 최소 길이는 1입니다. (길이가 0 이하로 자를 수는 없으니까요.)
            int left = 1;

            // 최대 길이는 branches 중 가장 긴 나뭇가지의 길이로 합니다.
            // 왜냐하면 그보다 더 긴 길이로는 당연히 나눌 수 없기 때문입니다.
            int right = 0;
            for (int branch : branches) {
                if (branch > right) {
                    right = branch;
                }
            }

            // result는 최종적으로 가능한 최대 길이를 저장할 변수입니다.
            // 아직 가능한 길이를 못 찾았으므로 -1로 초기화합니다.
            int result = -1;

            // 이진 탐색 시작:
            // left와 right 사이에서 "가능한 최대 길이"를 탐색합니다.
            // 가능한 길이인지 판단하는 기준: mid 길이로 나뭇가지를 잘랐을 때
            // 최소 N개 이상의 조각을 얻을 수 있는가?
            while (left <= right) {
                // 중간값 mid를 길이 후보로 삼습니다.
                int mid = left + (right - left) / 2;

                // mid 길이로 잘랐을 때 총 몇 개의 조각을 얻을 수 있는지 계산
                int count = 0;
                for (int branch : branches) {
                    // branch 하나를 mid 길이로 자를 때 몇 개 나오는가?
                    // 예: branch=10, mid=3일 때 10/3=3개 조각
                    count += branch / mid;

                    // 만약 N개 이상을 이미 만들 수 있다면 더 계산할 필요 없음
                    if (count >= N) {
                        break;
                    }
                }

                // count와 N을 비교해서 더 긴 길이로도 가능한지, 아니면 더 짧은 길이가 필요한지 결정
                if (count >= N) {
                    // mid 길이로 N개 이상 만들 수 있다면,
                    // 일단 가능한 길이이므로 result에 저장
                    result = mid;
                    // 그리고 더 긴 길이도 가능한지 확인하기 위해
                    // 범위를 확장(left를 mid+1로 이동)
                    left = mid + 1;
                } else {
                    // mid 길이로는 N개를 만들 수 없음
                    // 따라서 더 짧은 길이를 시도하기 위해 범위를 줄임(right = mid-1)
                    right = mid - 1;
                }
            }

            // 이진 탐색을 모두 마친 후, result에는 가능한 최대 길이가 저장되어 있습니다.
            // 만약 하나도 만들 수 없는 경우 -1 그대로 남게 됩니다.
            return result;
        }
    }

    // 필요하다면 main 메서드를 통해 간단히 테스트할 수 있습니다.
    // 예:
    // public static void main(String[] args) {
    //     zbct_14_4 outer = new zbct_14_4();
    //     zbct_14_4.Solution sol = outer.new Solution();
    //     int[] branches = {10, 15, 20};
    //     // 예를 들어, N=5라고 할 때, 길이 5로 자르면
    //     // 10에서 2개, 15에서 3개, 20에서 4개 총 9개 -> 가능
    //     // 더 긴 길이도 가능한지 확인해서 최대 길이를 찾을 수 있습니다.
    //     int result = sol.solution(5, branches);
    //     System.out.println(result);
    // }
}
