package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_14_3 {
    class Solution {
        public int solution(int[] arr) {
            // 피크를 찾을 때, 첫 번째 원소(arr[0])와 마지막 원소(arr[arr.length-1])는
            // 양 옆에 값이 없거나 부족하므로 피크가 될 수 없습니다.
            // 따라서 탐색 범위를 1부터 arr.length-2로 제한합니다.
            int left = 1;
            int right = arr.length - 2;

            // 이진 탐색을 통해 피크 인덱스를 찾습니다.
            // 이진 탐색: 범위를 절반씩 줄여가며 조건을 만족하는 인덱스를 탐색하는 방법
            while (left <= right) {
                // 중간 인덱스 mid를 구합니다.
                // (left와 right의 중간 지점)
                int mid = left + (right - left) / 2;

                // mid가 피크인지 확인:
                // 피크 조건: arr[mid-1] < arr[mid] && arr[mid] > arr[mid+1]
                // 즉, mid의 값이 양 옆보다 모두 커야 합니다.
                if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
                    // 피크를 찾았다면 해당 인덱스를 바로 반환
                    return mid;
                }

                // 피크를 찾지 못했다면, 현재 mid 주위의 "기울기"를 보고 탐색 방향을 결정합니다.
                // 만약 arr[mid-1] < arr[mid] 라면, 왼쪽에서 오른쪽으로 오르막을 타고 있다는 뜻입니다.
                // 즉, 아직 오른쪽으로 가면 더 높은 값이 나와 피크가 오른쪽에 있을 가능성이 큽니다.
                if (arr[mid - 1] < arr[mid]) {
                    // 오른쪽 부분에 피크가 있다고 판단하고 탐색 범위를 mid+1 이상으로 좁힙니다.
                    left = mid + 1;
                } else {
                    // 반대로 오른쪽에서 왼쪽 방향으로 기울어져 있거나
                    // arr[mid-1] >= arr[mid]인 경우, 왼쪽에 피크가 있을 수 있습니다.
                    // 따라서 탐색 범위를 mid-1 이하로 좁힙니다.
                    right = mid - 1;
                }
            }

            // 여기까지 왔다면 조건을 만족하는 피크 인덱스를 찾지 못했다는 의미이므로 -1 반환
            return -1;
        }
    }

    // 필요하다면 main 메서드를 통해 간단한 테스트를 할 수 있습니다.
    // 예시:
    // public static void main(String[] args) {
    //     zbct_14_3 outer = new zbct_14_3();
    //     zbct_14_3.Solution sol = outer.new Solution();
    //     int[] arr = {-3, 0, 3, 4, 5, 12, 15, 14, 12, 11};
    //     System.out.println(sol.solution(arr)); // 피크 인덱스 출력 (예: 6)
    // }
}
