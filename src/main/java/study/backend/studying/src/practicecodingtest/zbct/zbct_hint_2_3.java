package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Scanner;

public class zbct_hint_2_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //첫번째 숫자 입력받기
        long num1 = sc.nextLong();

        //연산자와 숫자를 번갈아가며 입력받음
        while (sc.hasNext()) {
            //연산자 입력받기
            String operator = sc.next();

            //연산자가 =인 경우 반복문 종료
            if (operator.equals("=")) {
                break;
            }

            //숫자 입력받기
            long num2 = sc.nextLong();

            //연산자에 따라 계산
            switch (operator) {
                case "+":
                    num1 += num2;
                    break;
                case "-":
                    num1 -= num2;
                    break;
                case "*":
                    num1 *= num2;
                    break;
                case "/":
                    num1 /= num2;
                    break;
            }
        }
    }
}