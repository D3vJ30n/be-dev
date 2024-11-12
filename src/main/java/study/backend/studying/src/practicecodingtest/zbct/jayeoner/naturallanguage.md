1. 문자열 조작 유형
   "주어진 문자열에서 각 알파벳의 개수를 세시오"

public int[] countAlphabets(String s) {
// 알파벳 26개의 출현 횟수를 저장할 배열 선언
int[] count = new int[26];  // 인덱스 0은 'a', 25는 'z'에 대응

    // 문자열을 소문자로 변환하고 각 문자를 순회
    for (char c : s.toLowerCase().toCharArray()) { // 뒤에서부터 읽자. 문자열을 소문자로 변환 후 c에 넣는다.
        // 현재 문자가 알파벳인 경우에만 처리
        if (Character.isLetter(c)) {
            // 'a'를 빼서 0-25 사이의 인덱스로 변환하여 개수 증가
            count[c - 'a']++;
        }
    }
    return count;
}

---

"문자열이 팰린드롬인지 확인하시오 (대소문자 구분하지 않고, 알파벳만 고려)"

public boolean isPalindrome(String s) {
// 1. 전처리: 알파벳과 숫자만 남기고 모두 소문자로 변환
// 정규식 [^A-Za-z0-9]: 알파벳과 숫자가 아닌 문자를 의미
s = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();

    // 2. 양 끝에서부터 중앙으로 이동하며 비교
    int left = 0;                    // 왼쪽 포인터
    int right = s.length() - 1;      // 오른쪽 포인터
    
    while (left < right) {
        // 양쪽 문자가 다르면 팰린드롬이 아님
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        // 양쪽에서 한 칸씩 중앙으로 이동
        left++;
        right--;
    }
    // 모든 비교가 끝나면 팰린드롬
    return true;
}

---

"두 문자열이 애너그램인지 확인하시오"

public boolean isAnagram(String s, String t) {
// 1. 길이가 다르면 애너그램이 될 수 없음
if (s.length() != t.length()) {
return false;
}

    // 2. 각 알파벳의 출현 횟수를 저장할 배열
    int[] charCount = new int[26];
    
    // 3. 첫 번째 문자열의 각 문자 처리
    for (char c : s.toCharArray()) {
        charCount[c - 'a']++;    // 해당 문자의 카운트 증가
    }
    
    // 4. 두 번째 문자열의 각 문자 처리
    for (char c : t.toCharArray()) {
        charCount[c - 'a']--;    // 해당 문자의 카운트 감소
        // 카운트가 음수가 되면 t에 있는 문자가 s보다 많은 것
        if (charCount[c - 'a'] < 0) {
            return false;
        }
    }
    
    // 5. 모든 카운트가 0이면 애너그램
    return true;
}

---

"문자열에서 연속되지 않은 중복 문자를 제거하시오"

public String removeDuplicates(String s) {
// 1. 각 문자의 마지막 등장 위치를 저장할 맵
HashMap<Character, Integer> lastIndex = new HashMap<>();

    // 2. 각 문자의 마지막 등장 위치 기록
    for (int i = 0; i < s.length(); i++) {
        lastIndex.put(s.charAt(i), i);
    }
    
    // 3. 문자를 선택적으로 포함할 StringBuilder
    StringBuilder result = new StringBuilder();
    // 이미 사용된 문자를 추적할 Set
    HashSet<Character> seen = new HashSet<>();
    
    // 4. 문자열을 순회하며 결과 생성
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        // 현재 문자가 아직 사용되지 않았고
        if (!seen.contains(c)) {
            // 이전 문자보다 현재 문자가 더 작고,
            // 이전 문자가 나중에도 등장하면 이전 문자 제거
            while (result.length() > 0 && 
                   c < result.charAt(result.length() - 1) && 
                   lastIndex.get(result.charAt(result.length() - 1)) > i) {
                seen.remove(result.charAt(result.length() - 1));
                result.setLength(result.length() - 1);
            }
            // 현재 문자 추가
            result.append(c);
            seen.add(c);
        }
    }
    
    return result.toString();
}

---

2. 배열/리스트 처리 유형
   "배열에서 두 수의 합이 target이 되는 조합을 찾으시오"

public int[] twoSum(int[] nums, int target) {
// 값과 인덱스를 저장할 HashMap 선언
// Key: 숫자값, Value: 인덱스
HashMap<Integer, Integer> map = new HashMap<>();

    // 배열을 한 번 순회하며 검사
    for (int i = 0; i < nums.length; i++) {
        // 현재 숫자와 더해서 target이 되는 값 계산
        int complement = target - nums[i];
        
        // 찾는 값이 HashMap에 있으면 결과 반환
        if (map.containsKey(complement)) {
            return new int[] {map.get(complement), i};
        }
        
        // 현재 숫자와 인덱스를 HashMap에 저장
        map.put(nums[i], i);
    }
    
    // 결과를 찾지 못한 경우
    return new int[] {};
}

---

"배열에서 가장 긴 연속된 증가 부분 수열의 길이를 구하시오"

public int findLongestIncreasing(int[] nums) {
// 배열이 비어있으면 0 반환
if (nums == null || nums.length == 0) {
return 0;
}

    // 현재까지의 최대 길이와 현재 연속 증가 길이
    int maxLength = 1;
    int currentLength = 1;
    
    // 배열을 순회하며 연속 증가 여부 확인
    for (int i = 1; i < nums.length; i++) {
        // 현재 숫자가 이전 숫자보다 크면
        if (nums[i] > nums[i-1]) {
            // 현재 연속 길이 증가
            currentLength++;
            // 최대 길이 갱신
            maxLength = Math.max(maxLength, currentLength);
        } else {
            // 연속이 끊기면 현재 길이 초기화
            currentLength = 1;
        }
    }
    
    return maxLength;
}

---

"배열의 모든 부분 배열 중 합이 가장 큰 구간을 찾으시오" (Kadane's Algorithm)

public int maxSubArray(int[] nums) {
// 배열이 비어있으면 0 반환
if (nums == null || nums.length == 0) {
return 0;
}

    // 현재까지의 부분합과 전체 최대합 초기화
    int currentSum = nums[0];    // 현재 위치까지의 최대 부분합
    int maxSum = nums[0];        // 전체 구간에서의 최대합
    
    // 두 번째 원소부터 순회
    for (int i = 1; i < nums.length; i++) {
        // 현재 원소를 더했을 때와 현재 원소부터 새로 시작했을 때 중 큰 값 선택
        currentSum = Math.max(nums[i], currentSum + nums[i]);
        // 전체 최대합 갱신
        maxSum = Math.max(maxSum, currentSum);
    }
    
    return maxSum;
}

---

"주어진 배열을 정렬했을 때 k번째로 큰 원소를 찾으시오"

public int findKthLargest(int[] nums, int k) {
// 우선순위 큐(최소 힙) 생성
// k개의 가장 큰 원소만 유지
PriorityQueue<Integer> pq = new PriorityQueue<>();

    // 배열의 모든 원소를 순회
    for (int num : nums) {
        // 현재 원소를 우선순위 큐에 추가
        pq.offer(num);
        
        // 큐의 크기가 k보다 크면 가장 작은 원소 제거
        if (pq.size() > k) {
            pq.poll();
        }
    }
    
    // k번째로 큰 수(큐의 맨 앞 원소) 반환
    return pq.peek();
}

---

3. 그래프/트리 탐색 유형
   "N×M 크기의 미로에서 도착점까지의 최단 거리를 구하시오" (BFS)

public int findShortestPath(int[][] maze) {
// 미로의 크기
int n = maze.length;
int m = maze[0].length;

    // 상하좌우 이동을 위한 방향 배열
    int[] dx = {-1, 1, 0, 0};  // 행 이동
    int[] dy = {0, 0, -1, 1};  // 열 이동
    
    // 방문 여부와 거리를 저장할 2차원 배열
    boolean[][] visited = new boolean[n][m];
    
    // BFS를 위한 큐 선언 (위치와 거리를 저장)
    Queue<int[]> queue = new LinkedList<>();
    
    // 시작점 (0,0) 추가 및 방문 처리
    queue.offer(new int[]{0, 0, 0});  // {x좌표, y좌표, 거리}
    visited[0][0] = true;
    
    // BFS 시작
    while (!queue.isEmpty()) {
        int[] current = queue.poll();
        int x = current[0];
        int y = current[1];
        int distance = current[2];
        
        // 도착점에 도달한 경우
        if (x == n-1 && y == m-1) {
            return distance;
        }
        
        // 네 방향으로 이동 시도
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            // 미로 범위 내이고, 벽이 아니며, 방문하지 않은 경우
            if (nx >= 0 && nx < n && ny >= 0 && ny < m 
                && maze[nx][ny] == 1 && !visited[nx][ny]) {
                visited[nx][ny] = true;
                queue.offer(new int[]{nx, ny, distance + 1});
            }
        }
    }
    
    // 도착점에 도달할 수 없는 경우
    return -1;
}

---

"이진 트리에서 루트에서 리프까지의 모든 경로를 출력하시오" (DFS)

// 이진 트리 노드 클래스
class TreeNode {
int val;
TreeNode left;
TreeNode right;
TreeNode(int val) {
this.val = val;
}
}

public class Solution {
// 모든 경로를 저장할 리스트
List<String> result = new ArrayList<>();

    public List<String> findPaths(TreeNode root) {
        // 빈 트리인 경우
        if (root == null) {
            return result;
        }
        
        // DFS 시작
        dfs(root, String.valueOf(root.val));
        return result;
    }
    
    // DFS 탐색 메소드
    private void dfs(TreeNode node, String path) {
        // 리프 노드인 경우 (더 이상 자식이 없는 경우)
        if (node.left == null && node.right == null) {
            result.add(path);
            return;
        }
        
        // 왼쪽 자식이 있는 경우
        if (node.left != null) {
            dfs(node.left, path + "->" + node.left.val);
        }
        
        // 오른쪽 자식이 있는 경우
        if (node.right != null) {
            dfs(node.right, path + "->" + node.right.val);
        }
    }
}

---

"그래프에서 두 정점 사이의 모든 경로를 찾으시오" (DFS with Backtracking)

public class Solution {
// 결과를 저장할 리스트
List<List<Integer>> allPaths = new ArrayList<>();

    public List<List<Integer>> findAllPaths(int[][] graph, int start, int end) {
        // 현재 경로를 저장할 리스트
        List<Integer> path = new ArrayList<>();
        // 방문 여부를 저장할 배열
        boolean[] visited = new boolean[graph.length];
        
        // 시작점 추가 및 방문 처리
        path.add(start);
        visited[start] = true;
        
        // DFS 시작
        dfs(graph, start, end, visited, path);
        
        return allPaths;
    }
    
    private void dfs(int[][] graph, int current, int end,
                    boolean[] visited, List<Integer> path) {
        // 목적지에 도달한 경우
        if (current == end) {
            allPaths.add(new ArrayList<>(path));
            return;
        }
        
        // 현재 정점과 연결된 모든 정점 탐색
        for (int next = 0; next < graph.length; next++) {
            // 연결되어 있고 방문하지 않은 정점인 경우
            if (graph[current][next] == 1 && !visited[next]) {
                // 방문 처리 및 경로에 추가
                visited[next] = true;
                path.add(next);
                
                // 다음 정점으로 DFS
                dfs(graph, next, end, visited, path);
                
                // 백트래킹: 방문 취소 및 경로에서 제거
                visited[next] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}

---

4. 동적 프로그래밍(DP) 유형
   "계단을 오를 때 한 번에 1계단 또는 2계단씩 오를 수 있다. n개의 계단을 오르는 방법의 수를 구하시오"

public class Solution {
public int climbStairs(int n) {
// n이 1이하인 경우 바로 반환
if (n <= 1) return 1;

        // dp 배열 초기화
        // dp[i]: i번째 계단까지 도달하는 방법의 수
        int[] dp = new int[n + 1];
        
        // 기본 케이스 초기화
        dp[0] = 1;  // 시작점에 도달하는 방법: 1가지
        dp[1] = 1;  // 1계단에 도달하는 방법: 1가지
        
        // 2계단부터 n계단까지 계산
        for (int i = 2; i <= n; i++) {
            // i번째 계단에 도달하는 방법
            // = (i-1)번째 계단에서 1계단 오르는 방법
            // + (i-2)번째 계단에서 2계단 오르는 방법
            dp[i] = dp[i-1] + dp[i-2];
        }
        
        return dp[n];
    }
}

---

"최대 무게 W까지 담을 수 있는 배낭에 가치가 최대가 되도록 물건을 담으시오" (0/1 Knapsack)

public class Solution {
public int knapsack(int W, int[] weights, int[] values, int n) {
// dp[i][w]: i번째 물건까지 고려했을 때,
// 무게 w까지 담을 수 있는 최대 가치
int[][] dp = new int[n + 1][W + 1];

        // 모든 물건에 대해 반복
        for (int i = 1; i <= n; i++) {
            // 각 무게 제한에 대해 반복
            for (int w = 1; w <= W; w++) {
                // 현재 물건을 넣을 수 없는 경우
                if (weights[i-1] > w) {
                    // 이전 물건까지의 최대 가치를 그대로 사용
                    dp[i][w] = dp[i-1][w];
                }
                // 현재 물건을 넣을 수 있는 경우
                else {
                    // max(현재 물건을 넣지 않는 경우, 현재 물건을 넣는 경우)
                    dp[i][w] = Math.max(
                        dp[i-1][w],  // 현재 물건을 넣지 않음
                        dp[i-1][w-weights[i-1]] + values[i-1]  // 현재 물건을 넣음
                    );
                }
            }
        }
        
        return dp[n][W];
    }
}

---

"주어진 수열에서 가장 긴 증가하는 부분 수열의 길이를 구하시오" (LIS)

public class Solution {
public int lengthOfLIS(int[] nums) {
// 배열이 비어있는 경우
if (nums == null || nums.length == 0) {
return 0;
}

        int n = nums.length;
        
        // dp[i]: i번째 원소를 마지막으로 하는 
        // 증가하는 부분 수열의 최대 길이
        int[] dp = new int[n];
        
        // 모든 원소의 기본 길이는 1
        Arrays.fill(dp, 1);
        
        // 최대 길이를 저장할 변수
        int maxLength = 1;
        
        // 각 위치에서 가능한 최대 길이 계산
        for (int i = 1; i < n; i++) {
            // i 이전의 모든 원소들과 비교
            for (int j = 0; j < i; j++) {
                // 현재 원소가 이전 원소보다 크면 증가 수열 가능
                if (nums[i] > nums[j]) {
                    // 이전까지의 길이 + 1과 현재 길이 중 큰 값 선택
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            // 전체 최대 길이 갱신
            maxLength = Math.max(maxLength, dp[i]);
        }
        
        return maxLength;
    }
}

---

5. 이진 탐색(Binary Search) 유형
   "정렬된 배열에서 특정 값이 처음 등장하는 위치와 마지막으로 등장하는 위치를 찾으시오"

public class Solution {
public int[] searchRange(int[] nums, int target) {
// 결과를 저장할 배열 [-1, -1]로 초기화
int[] result = {-1, -1};

        // 첫 번째 위치 찾기
        result[0] = findFirstPosition(nums, target);
        // 마지막 위치 찾기
        result[1] = findLastPosition(nums, target);
        
        return result;
    }
    
    // 첫 번째 위치를 찾는 메소드
    private int findFirstPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                // 첫 번째 위치를 찾기 위해 왼쪽도 계속 탐색
                if (mid == 0 || nums[mid - 1] != target) {
                    return mid;
                }
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    // 마지막 위치를 찾는 메소드
    private int findLastPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                // 마지막 위치를 찾기 위해 오른쪽도 계속 탐색
                if (mid == nums.length - 1 || nums[mid + 1] != target) {
                    return mid;
                }
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}

---

"x의 제곱근을 구하시오 (소수점 이하는 버림)"

public class Solution {
public int mySqrt(int x) {
// x가 0 또는 1인 경우 바로 반환
if (x <= 1) return x;

        // 이진 탐색 범위 설정
        long left = 1;  // long 타입 사용 (오버플로우 방지)
        long right = x;
        
        // 결과값 저장 변수
        int result = 0;
        
        // 이진 탐색 시작
        while (left <= right) {
            // 중간값 계산
            long mid = left + (right - left) / 2;
            
            // 제곱값 계산
            long sqr = mid * mid;
            
            // 제곱값이 x와 같으면 mid가 정답
            if (sqr == x) {
                return (int)mid;
            }
            // 제곱값이 x보다 작으면 
            // 더 큰 값을 탐색하고 현재값 저장
            else if (sqr < x) {
                left = mid + 1;
                result = (int)mid;  // 가능한 답 저장
            }
            // 제곱값이 x보다 크면 더 작은 값 탐색
            else {
                right = mid - 1;
            }
        }
        
        return result;
    }
}

---

"정렬된 2차원 배열에서 특정 값을 찾으시오"

public class Solution {
public boolean searchMatrix(int[][] matrix, int target) {
// 행렬이 비어있는 경우 체크
if (matrix == null || matrix.length == 0 ||
matrix[0].length == 0) {
return false;
}

        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 왼쪽 아래 모서리에서 시작
        int row = rows - 1;  // 마지막 행
        int col = 0;         // 첫 번째 열
        
        // 행렬 범위를 벗어나지 않을 때까지 반복
        while (row >= 0 && col < cols) {
            // 현재 위치의 값과 target 비교
            if (matrix[row][col] == target) {
                return true;
            }
            // 현재 값이 target보다 크면 위로 이동
            else if (matrix[row][col] > target) {
                row--;
            }
            // 현재 값이 target보다 작으면 오른쪽으로 이동
            else {
                col++;
            }
        }
        
        // target을 찾지 못한 경우
        return false;
    }
}

---

6. 그리디(Greedy) 알고리즘 유형
   "회의실 배정: N개의 회의에 대해 회의실을 사용할 수 있는 최대 회의 수를 구하시오"

public class Solution {
public int maxMeetings(int[] start, int[] end) {
int n = start.length;

        // 회의 정보를 저장할 클래스
        class Meeting {
            int start, end;
            Meeting(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
        
        // 회의 목록 생성
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i]);
        }
        
        // 종료 시간 기준으로 정렬
        Arrays.sort(meetings, (a, b) -> a.end - b.end);
        
        int count = 1;  // 첫 번째 회의는 무조건 선택
        int endTime = meetings[0].end;  // 현재 회의 종료 시간
        
        // 나머지 회의들 확인
        for (int i = 1; i < n; i++) {
            // 현재 회의의 시작 시간이 
            // 이전 회의의 종료 시간보다 크거나 같으면 선택
            if (meetings[i].start >= endTime) {
                count++;
                endTime = meetings[i].end;
            }
        }
        
        return count;
    }
}

---

"동전 거스름돈: 가장 적은 수의 동전으로 거스름돈을 만드시오"

public class Solution {
public int minCoins(int[] coins, int amount) {
// 동전을 내림차순으로 정렬
// (큰 동전부터 사용하는 것이 최적)
Arrays.sort(coins);
int[] sortedCoins = new int[coins.length];
for(int i = 0; i < coins.length; i++) {
sortedCoins[i] = coins[coins.length-1-i];
}

        int totalCoins = 0;  // 사용된 동전의 총 개수
        int remainingAmount = amount;  // 남은 금액
        
        // 큰 동전부터 차례로 사용
        for (int coin : sortedCoins) {
            if (remainingAmount >= coin) {
                // 현재 동전으로 만들 수 있는 최대 개수
                int count = remainingAmount / coin;
                totalCoins += count;
                // 남은 금액 갱신
                remainingAmount -= (count * coin);
            }
            
            // 남은 금액이 0이면 종료
            if (remainingAmount == 0) {
                break;
            }
        }
        
        // 거스름돈을 만들 수 없는 경우
        return remainingAmount > 0 ? -1 : totalCoins;
    }
}

---

"주식을 사고팔아 얻을 수 있는 최대 이익을 구하시오 (여러 번 거래 가능)"

public class Solution {
public int maxProfit(int[] prices) {
// 배열이 비어있거나 하나의 가격만 있는 경우
if (prices == null || prices.length <= 1) return 0;

        int totalProfit = 0;
        
        // 연속된 두 날짜의 가격을 비교하며
        // 이익이 발생하는 모든 구간의 이익을 더함
        for (int i = 1; i < prices.length; i++) {
            // 현재 가격이 이전 가격보다 높으면
            // 그 차이만큼 이익 발생
            if (prices[i] > prices[i-1]) {
                totalProfit += prices[i] - prices[i-1];
            }
        }
        
        return totalProfit;
    }
    
    // 다른 방식의 구현 (저점 매수, 고점 매도)
    public int maxProfitAlternative(int[] prices) {
        int totalProfit = 0;
        int buyPrice = Integer.MAX_VALUE;  // 매수 가격
        
        for (int price : prices) {
            // 현재 가격이 매수 가격보다 낮으면 매수
            if (price < buyPrice) {
                buyPrice = price;
            }
            // 현재 가격이 매수 가격보다 높으면 매도
            else if (price > buyPrice) {
                totalProfit += price - buyPrice;
                buyPrice = price;  // 다음 거래를 위해 갱신
            }
        }
        
        return totalProfit;
    }
}

---

7. 해시 테이블(Hash Table) 활용 유형
   "문자열에서 중복 없이 가장 긴 부분 문자열의 길이를 구하시오"

public class Solution {
public int lengthOfLongestSubstring(String s) {
// 각 문자의 마지막 등장 위치를 저장할 HashMap
HashMap<Character, Integer> lastSeen = new HashMap<>();

        int maxLength = 0;    // 가장 긴 길이
        int start = 0;        // 현재 부분 문자열의 시작 위치
        
        // 문자열을 순회하며 검사
        for (int end = 0; end < s.length(); end++) {
            char currentChar = s.charAt(end);
            
            // 현재 문자가 이미 등장했고, 
            // 그 위치가 현재 부분 문자열 내에 있는 경우
            if (lastSeen.containsKey(currentChar) && 
                lastSeen.get(currentChar) >= start) {
                // 시작 위치를 중복된 문자 다음으로 이동
                start = lastSeen.get(currentChar) + 1;
            }
            
            // 현재 문자의 위치 저장
            lastSeen.put(currentChar, end);
            
            // 최대 길이 갱신
            maxLength = Math.max(maxLength, end - start + 1);
        }
        
        return maxLength;
    }
}

---

"두 배열의 교집합을 구하시오 (중복 허용)"

public class Solution {
public int[] intersect(int[] nums1, int[] nums2) {
// 첫 번째 배열의 각 숫자의 등장 횟수를 저장
HashMap<Integer, Integer> map = new HashMap<>();

        // 결과를 저장할 ArrayList
        ArrayList<Integer> result = new ArrayList<>();
        
        // 첫 번째 배열의 숫자 개수 세기
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        
        // 두 번째 배열을 순회하며 교집합 찾기
        for (int num : nums2) {
            // 현재 숫자가 map에 있고 개수가 1 이상인 경우
            if (map.containsKey(num) && map.get(num) > 0) {
                result.add(num);
                // 해당 숫자의 개수 감소
                map.put(num, map.get(num) - 1);
            }
        }
        
        // ArrayList를 배열로 변환
        int[] answer = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }
        
        return answer;
    }
}

---

"문자열에서 k번 이상 등장하는 문자를 모두 찾으시오"

public class Solution {
public List<Character> findFrequentChars(String s, int k) {
// 각 문자의 등장 횟수를 저장할 HashMap
HashMap<Character, Integer> frequency = new HashMap<>();

        // 결과를 저장할 List
        List<Character> result = new ArrayList<>();
        
        // 각 문자의 등장 횟수 계산
        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        
        // k번 이상 등장하는 문자 찾기
        for (Map.Entry<Character, Integer> entry : 
             frequency.entrySet()) {
            if (entry.getValue() >= k) {
                result.add(entry.getKey());
            }
        }
        
        // 결과를 알파벳 순으로 정렬 (선택사항)
        Collections.sort(result);
        
        return result;
    }
    
    // 최적화된 버전 (배열 사용)
    public List<Character> findFrequentCharsOptimized(String s, int k) {
        // 알파벳 개수만큼의 배열 사용 (더 빠름)
        int[] count = new int[26];  // 소문자만 가정
        List<Character> result = new ArrayList<>();
        
        // 각 문자의 등장 횟수 계산
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        
        // k번 이상 등장하는 문자 찾기
        for (int i = 0; i < 26; i++) {
            if (count[i] >= k) {
                result.add((char)(i + 'a'));
            }
        }
        
        return result;
    }
}

---

8. 투 포인터(Two Pointers) 유형
   "정렬된 배열에서 두 수의 합이 target이 되는 위치를 찾으시오"

public class Solution {
public int[] twoSum(int[] numbers, int target) {
// 왼쪽과 오른쪽 포인터 초기화
int left = 0;
int right = numbers.length - 1;

        while (left < right) {
            // 현재 두 수의 합 계산
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                // 문제에서 1-based index를 요구하는 경우
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                // 합이 작으면 왼쪽 포인터를 오른쪽으로
                left++;
            } else {
                // 합이 크면 오른쪽 포인터를 왼쪽으로
                right--;
            }
        }
        
        // 답이 없는 경우
        return new int[]{-1, -1};
    }
}

---

"배열에서 연속된 0의 최대 길이를 k번의 1로 바꾸기로 만들 수 있는 길이를 구하시오"

public class Solution {
public int longestOnes(int[] nums, int k) {
int maxLength = 0;    // 최대 길이
int zeroCount = 0;    // 현재 윈도우의 0의 개수
int left = 0;         // 왼쪽 포인터

        // 오른쪽 포인터를 이동하며 확인
        for (int right = 0; right < nums.length; right++) {
            // 현재 숫자가 0이면 카운트 증가
            if (nums[right] == 0) {
                zeroCount++;
            }
            
            // 0의 개수가 k를 초과하면 윈도우 줄이기
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            
            // 현재 윈도우의 길이로 최대 길이 갱신
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}

---

"세 수의 합이 0이 되는 모든 조합을 찾으시오"

public class Solution {
public List<List<Integer>> threeSum(int[] nums) {
List<List<Integer>> result = new ArrayList<>();

        // 1. 배열 정렬
        Arrays.sort(nums);
        
        // 2. 첫 번째 수를 고정하고 나머지 두 수를 투 포인터로 찾기
        for (int i = 0; i < nums.length - 2; i++) {
            // 중복된 첫 번째 수 건너뛰기
            if (i > 0 && nums[i] == nums[i-1]) continue;
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    // 세 수의 합이 0인 경우 결과에 추가
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 중복 건너뛰기
                    while (left < right && nums[left] == nums[left+1]) left++;
                    while (left < right && nums[right] == nums[right-1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
}

---

10. 스택/큐 활용 유형
    "괄호가 유효한지 확인하시오 ((), [], {} 조합)"

public class Solution {
public boolean isValidParentheses(String s) {
// 괄호를 저장할 스택
Stack<Character> stack = new Stack<>();

        // 각 문자를 순회하며 확인
        for (char c : s.toCharArray()) {
            // 여는 괄호인 경우 스택에 추가
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            // 닫는 괄호인 경우
            else {
                // 스택이 비어있으면 잘못된 경우
                if (stack.isEmpty()) {
                    return false;
                }
                
                // 스택의 top과 현재 괄호가 매칭되는지 확인
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        
        // 스택이 비어있어야 모든 괄호가 매칭된 것
        return stack.isEmpty();
    }
}

---

"히스토그램에서 가장 큰 직사각형의 넓이를 구하시오"

public class Solution {
public int largestRectangleArea(int[] heights) {
// 높이의 인덱스를 저장할 스택
Stack<Integer> stack = new Stack<>();
int maxArea = 0;
int i = 0;

        while (i < heights.length) {
            // 스택이 비어있거나 현재 높이가 더 큰 경우
            if (stack.isEmpty() || 
                heights[stack.peek()] <= heights[i]) {
                stack.push(i++);
            }
            // 현재 높이가 더 작은 경우
            else {
                // 스택에서 높이를 꺼내어 넓이 계산
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
        }
        
        // 스택에 남아있는 높이들 처리
        while (!stack.isEmpty()) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        return maxArea;
    }
}

---

"최근 k개의 요청의 평균 시간을 구하는 시스템을 구현하시오"

class MovingAverage {
private Queue<Integer> queue;
private int size;
private double sum;

    public MovingAverage(int size) {
        // 고정 크기의 큐 초기화
        this.queue = new LinkedList<>();
        this.size = size;
        this.sum = 0.0;
    }
    
    public double next(int val) {
        // 큐가 꽉 찼으면 가장 오래된 값 제거
        if (queue.size() == size) {
            sum -= queue.poll();
        }
        
        // 새로운 값 추가
        queue.offer(val);
        sum += val;
        
        // 평균 반환
        return sum / queue.size();
    }
}

---

"일일 온도: 더 따뜻한 날까지 기다려야 하는 일수를 구하시오"

public class Solution {
public int[] dailyTemperatures(int[] temperatures) {
int n = temperatures.length;
int[] answer = new int[n];
// 인덱스를 저장할 스택
Stack<Integer> stack = new Stack<>();

        // 모든 온도를 순회
        for (int i = 0; i < n; i++) {
            // 현재 온도가 스택 top의 온도보다 높으면
            // 스택에서 pop하며 결과 업데이트
            while (!stack.isEmpty() && 
                   temperatures[stack.peek()] < temperatures[i]) {
                int prevDay = stack.pop();
                answer[prevDay] = i - prevDay;
            }
            // 현재 날짜 인덱스를 스택에 저장
            stack.push(i);
        }
        
        return answer;
    }
}

스택/큐 활용시 주요 포인트

스택: LIFO(Last In First Out) 구조

괄호 매칭
히스토그램
온도 차이 계산 등에 유용


큐: FIFO(First In First Out) 구조

순서대로 처리가 필요한 경우
최근 k개의 데이터 관리
BFS 구현 등에 유용


시간복잡도:

삽입/삭제: O(1)
접근/검색: O(n)