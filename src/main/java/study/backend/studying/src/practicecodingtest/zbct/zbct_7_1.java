package study.backend.studying.src.practicecodingtest.zbct;

class zbct_7_1 {
    public int solution(int[] usageArr, int fee) {
        // Stream API를 사용하는 방법
        // return Arrays.stream(usageArr).sum() * fee;

        // 전통적인 for문을 사용하는 방법 (더 직관적이고 성능상 유리)
        int totalUsage = 0;
        for (int usage : usageArr) {
            totalUsage += usage;
        }
        return totalUsage * fee;
    }
}