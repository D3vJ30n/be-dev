package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_8_4 {
    public int solution(int A, int B) {
        // A와 B의 XOR 연산으로 다른 비트만 1로 만든 후
        // Integer.bitCount()로 1의 개수(다른 비트의 개수)를 세어 반환
        return Integer.bitCount(A ^ B);
    }
}