package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Stack;

public class zbct_3_3 {
    public int solution(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            // 스택이 비어있지 않고 현재 문자와 스택의 top이 같으면
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop(); // 두 문자 모두 제거 (top을 제거)
            } else {
                stack.push(c); // 그렇지 않으면 현재 문자 추가
            }
        }
        // 스택이 비어있으면 1, 아니면 0 반환
        return stack.isEmpty() ? 1 : 0;
    }
}