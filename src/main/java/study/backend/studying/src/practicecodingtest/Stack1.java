//// 문제 : https://www.acmicpc.net/problem/9012
//
//package study.backend.studying.src.practicecodingtest;
//
//import java.util.Scanner;  // 사용자 입력을 받기 위한 Scanner 클래스 import
//import java.util.Stack;    // 괄호 검사를 위한 Stack 클래스 import
//
///*
//문제 해결 과정:
//1. 문제 분석
//  - 괄호 문자열이 올바른지(VPS) 판단해야 함
//  - 올바른 괄호: "()", "(())", "(()))", "()()" 등
//  - 잘못된 괄호: ")(", "(()("", "(()" 등
//
//2. 해결 방법 구상
//  - 스택을 사용하여 괄호 매칭 검사
//  - 여는 괄호는 스택에 저장
//  - 닫는 괄호가 나오면 스택에서 여는 괄호를 제거
//  - 최종적으로 스택이 비어있어야 올바른 괄호 문자열
//
//3. 의사코드:
//  함수 isVPS(문자열 s):
//      스택 생성
//
//      각 문자 c에 대해:
//          만약 c가 '('이면:
//              스택에 push
//          c가 ')'이면:
//              만약 스택이 비어있으면:
//                  return "NO"
//              스택에서 pop
//
//      만약 스택이 비어있으면:
//          return "YES"
//      아니면:
//          return "NO"
//*/
//
//public class Stack1 {
//    // VPS(Valid Parenthesis String) 검사 메소드
//    // 입력받은 괄호 문자열이 올바른 괄호 문자열인지 검사
//    public static String isVPS(String s) {
//        // 괄호를 저장할 Stack 생성 (Character 타입 사용)
//        Stack<Character> stack = new Stack<>();
//
//        // 입력받은 문자열의 각 문자를 순회
//        for(int i = 0; i < s.length(); i++) {
//            // 현재 검사할 문자
//            char c = s.charAt(i);
//
//            // 여는 괄호('(')인 경우
//            if(c == '(') {
//                stack.push(c);    // 스택에 추가
//            }
//            // 닫는 괄호(')')인 경우
//            else if(c == ')') {
//                // 스택이 비어있으면 올바르지 않은 괄호 문자열
//                // (닫는 괄호가 더 많은 경우)
//                if(stack.empty()) {
//                    return "NO";
//                }
//                stack.pop();    // 스택에서 가장 최근의 여는 괄호 제거
//            }
//        }
//
//        // 모든 문자 검사 후 스택이 비어있는지 확인
//        if(stack.empty()) {
//            return "YES";    // 스택이 비어있으면 올바른 괄호 문자열
//        } else {
//            return "NO";     // 스택에 괄호가 남아있으면 올바르지 않은 괄호 문자열
//            // (여는 괄호가 더 많은 경우)
//        }
//    }
//
//    public static void main(String[] args) {
//        // Scanner 객체 생성
//        Scanner sc = new Scanner(System.in);
//
//        // 테스트 케이스의 개수 입력 받기
//        int T = sc.nextInt();
//        sc.nextLine();    // 개행문자 제거 (다음 입력을 위해)
//
//        // 각 테스트 케이스 처리
//        for(int i = 0; i < T; i++) {
//            String str = sc.nextLine();    // 괄호 문자열 입력 받기
//            System.out.println(isVPS(str)); // 결과 출력
//        }
//
//        // Scanner 객체 닫기 (리소스 해제)
//        sc.close();
//    }
//}
//
///*
//시간 복잡도 분석:
//- 각 문자열을 한 번씩 순회: O(n) (n은 문자열 길이)
//- 스택 연산(push, pop)은 O(1)
//- 전체 시간 복잡도: O(n)
//
//공간 복잡도 분석:
//- 스택 저장 공간: O(n) (최악의 경우 모든 문자가 여는 괄호)
//- 전체 공간 복잡도: O(n)
//
//테스트 케이스 분석:
//1. 기본 케이스:
//  - "()" → "YES"
//  - "(())" → "YES"
//
//2. 복합 케이스:
//  - "(())()" → "YES"
//  - "((()))" → "YES"
//
//3. 에러 케이스:
//  - ")" → "NO" (닫는 괄호로 시작)
//  - "(" → "NO" (여는 괄호만 있음)
//  - "())" → "NO" (닫는 괄호가 더 많음)
//*/