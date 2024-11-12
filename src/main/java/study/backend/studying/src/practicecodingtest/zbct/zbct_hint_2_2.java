package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_hint_2_2 {
    public int[] solution(int[] numbers, String direction) {
        if (numbers == null || numbers.length == 0) {
            return new int[]{};
        }

        if (direction.equals("right")) {
            // 마지막 원소를 저장 = numbers[마지막인덱스]
            int last = numbers[numbers.length - 1];
            // for i = 마지막 인덱스부터 1까지 감소
            for (int i = numbers.length - 1; i > 0; i--) {
                // numbers[i] = numbers[i-1]
                numbers[i] = numbers[i - 1];
            }
            numbers[0] = last;

        } else if (direction.equals("left")) {
            // 첫번째 원소를 저장 = numbers[0]
            int first = numbers[0];
            // for i = 0부터 numbers.length-2까지 증가
            for (int i = 0; i < numbers.length - 1; i++) {
                // numbers[i] = numbers[i+1]
                numbers[i] = numbers[i + 1];
            }
            numbers[numbers.length - 1] = first;
        }

        return numbers;
    }
}