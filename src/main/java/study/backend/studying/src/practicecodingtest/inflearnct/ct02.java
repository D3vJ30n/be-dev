package study.backend.studying.src.practicecodingtest.inflearnct;

import java.util.Scanner;

public class ct02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 입력 받기
        String input = scanner.nextLine();

        // 변환할 결과를 저장할 StringBuilder
        StringBuilder result = new StringBuilder();

        // 각 문자에 대해 대소문자 변환
        for (char c : input.toCharArray()) {
            // 대문자는 소문자로, 소문자는 대문자로 변환
            result.append(Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
        }

        // 결과 출력
        System.out.println(result);

        // Scanner 종료
        scanner.close();
    }
}