package study.backend.studying.src.practicecodingtest.inflearnct;

import java.util.Scanner;

public class ct01 {

    public static void main(String[] args) {

        /*
        문제 설명:
        - 한 개의 문자열을 입력받고, 특정 문자를 입력받아 해당 특정 문자가 입력받은 문자열에 몇 개 존재하는지 알아내는 프로그램을 작성하세요.
        - 대소문자를 구분하지 않습니다. 문자열의 길이는 100을 넘지 않습니다.

        입력 형식:
        1. 첫 번째 줄에 문자열이 주어집니다.
        2. 두 번째 줄에 특정 문자가 주어집니다.

        출력 형식:
        - 첫 줄에 해당 문자의 개수를 출력합니다.

        예시 입력 1:
        Computercooler
        c

        예시 출력 1:
        2
        */

        // Scanner 객체를 생성하여 사용자로부터 입력을 받음
        Scanner sc = new Scanner(System.in);

        // 첫 번째 줄에서 문자열을 입력받고, 대소문자를 구분하지 않기 위해 toUpperCase()로 모두 대문자로 변환
        // 입력 예시: "Computercooler" -> "COMPUTERCOOLER"
        System.out.print("문자열을 입력하세요: ");
        String str = sc.nextLine().toUpperCase();  // 전체 문자열을 대문자로 변환

        // 두 번째 줄에서 특정 문자를 입력받고, 역시 대소문자 구분을 하지 않기 위해 대문자로 변환
        // 입력 예시: 'c' -> 'C'
        System.out.print("찾고 싶은 문자를 입력하세요: ");
        char targetChar = sc.next().toUpperCase().charAt(0);  // 첫 번째 문자를 대문자로 변환

        // targetChar(특정 문자)의 개수를 셀 변수를 초기화, 초기값은 0
        int count = 0;

        // 문자열의 길이만큼 반복문을 돌며 각 문자가 targetChar와 일치하는지 확인
        // 문자열의 각 문자를 하나씩 확인하면서, 일치하면 count 값을 1 증가
        for (int i = 0; i < str.length(); i++) {
            // str.charAt(i)는 문자열 str에서 인덱스 i에 위치한 문자를 가져옴
            // 만약 해당 문자가 targetChar와 같다면 count를 증가시킴
            if (str.charAt(i) == targetChar) {
                count++;  // 문자가 일치할 때마다 count를 증가
            }
        }

        // 최종적으로 targetChar의 개수를 출력
        // 출력 예시: 'C'의 개수가 2개라면 결과는 "2" 출력
        System.out.println("입력한 문자 '" + targetChar + "'의 개수: " + count);
    }
}
