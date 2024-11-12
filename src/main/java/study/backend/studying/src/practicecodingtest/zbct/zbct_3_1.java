package study.backend.studying.src.practicecodingtest.zbct;

import java.util.*;

class zbct_3_1 {
    public String solution(String s) {
        // 문자를 저장할 스택 생성
        Stack<Character> stack = new Stack<>();

        // 입력 문자열의 각 문자를 확인
        for (char c : s.toCharArray()) {
            // 스택이 비어있지 않고, 현재 문자가 스택의 top과 같으면 제거
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            }
            // 그렇지 않으면 현재 문자를 스택에 추가
            else {
                stack.push(c);
            }
        }
        // 최종 결과 문자열 생성
        StringBuilder result = new StringBuilder();
        for (char c : stack) {
            result.append(c);
        }
        // 결과 반환
        return result.toString();
    }
}