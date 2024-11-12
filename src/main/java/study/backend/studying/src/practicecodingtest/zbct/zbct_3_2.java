package study.backend.studying.src.practicecodingtest.zbct;

import java.util.*;

class zbct_3_2 {
    public int solution(String s) {
        // 괄호를 순서대로 저장할 스택 선언
        // 여는 괄호만 저장하고, 닫는 괄호가 나오면 매칭되는 여는 괄호와 비교
        Stack<Character> stack = new Stack<>();

        // 문자열의 각 문자를 하나씩 검사
        for (int i = 0; i < s.length(); i++) {
            // 현재 검사할 문자
            char current = s.charAt(i);

            // 여는 괄호인 경우 ('(', '{', '[', '<')
            // 스택에 저장했다가 나중에 매칭되는 닫는 괄호가 나올 때 검사
            if (current == '(' || current == '{' || current == '[' || current == '<') {
                stack.push(current);  // 스택에 추가
            }
            // 닫는 괄호인 경우 (')', '}', ']', '>')
            else if (current == ')' || current == '}' || current == ']' || current == '>') {
                // 스택이 비어있는데 닫는 괄호가 나온 경우
                // 매칭될 여는 괄호가 없으므로 올바르지 않은 괄호 문자열
                if (stack.isEmpty()) {
                    return 0;  // 실패 반환
                }

                // 스택의 top에 있는 여는 괄호를 꺼내서 현재 닫는 괄호와 매칭되는지 검사
                char last = stack.pop();

                // 괄호 쌍이 맞지 않는 네 가지 경우를 검사
                // 1. ')' 인데 마지막 여는 괄호가 '('가 아닌 경우
                // 2. '}' 인데 마지막 여는 괄호가 '{'가 아닌 경우
                // 3. ']' 인데 마지막 여는 괄호가 '['가 아닌 경우
                // 4. '>' 인데 마지막 여는 괄호가 '<'가 아닌 경우
                if ((current == ')' && last != '(') ||
                    (current == '}' && last != '{') ||
                    (current == ']' && last != '[') ||
                    (current == '>' && last != '<')) {
                    return 0;  // 괄호 쌍이 맞지 않으면 실패 반환
                }
            }
        }

        // 모든 문자 검사가 끝난 후
        // 스택이 비어있으면 모든 괄호가 올바르게 매칭된 것
        // 스택에 괄호가 남아있으면 닫히지 않은 괄호가 있다는 것
        return stack.isEmpty() ? 1 : 0;
    }
}

/*
실행 예시:
1) s = "((()))"
   - 스택: ( ( (
   - ) 만나면 ( 꺼내서 매칭
   - ) 만나면 ( 꺼내서 매칭
   - ) 만나면 ( 꺼내서 매칭
   - 스택이 비어있으므로 1 반환

2) s = "(()"
   - 스택: ( (
   - ) 만나면 ( 꺼내서 매칭
   - 문자열 끝났는데 스택에 ( 가 남아있어서 0 반환

시간복잡도: O(n) - 문자열의 길이만큼 한 번 순회
공간복잡도: O(n) - 최악의 경우 스택에 문자열 길이만큼 저장
*/