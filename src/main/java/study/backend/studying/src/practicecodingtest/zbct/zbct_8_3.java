package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_8_3 {
    public double solution(String S) {
        // 현재까지 계산된 값을 저장하는 변수
        double currentNumber = 0;
        // 다음에 계산할 숫자를 저장하는 변수
        double nextNumber = 0;
        // 현재 처리해야 할 연산자 (초기값은 +로 설정)
        char operation = '+';
        // 최종 결과값 (덧셈, 뺄셈의 결과를 누적)
        double finalResult = 0;
        // 숫자를 문자열로 만들기 위한 StringBuilder
        StringBuilder numberBuilder = new StringBuilder();

        // 문자열을 한 글자씩 순회
        for (int i = 0; i < S.length(); i++) {
            char currentChar = S.charAt(i);

            // 현재 문자가 숫자인 경우
            if (Character.isDigit(currentChar)) { // isDigit 주어진 문자가 숫자인지 확인하는 메소드
                // StringBuilder에 숫자를 추가 (여러 자릿수 처리를 위해)
                numberBuilder.append(currentChar);
            }

            if (!Character.isDigit(currentChar) || i == S.length() - 1) { // 현재 문자가 숫자가 아닐 때 또는 마지막 숫자인 경우
                // numberBuilder에 숫자가 있는 경우에만 처리
                if (numberBuilder.length() > 0) {
                    // StringBuilder에 저장된 숫자 문자열을 실제 숫자로 변환
                    nextNumber = Double.parseDouble(numberBuilder.toString());
                    // StringBuilder 초기화 (다음 숫자를 위해)
                    numberBuilder = new StringBuilder();

                    // 이전 연산자에 따라 계산 수행
                    switch (operation) {
                        case '*':
                            // 곱셈: 현재 값과 다음 숫자를 곱함
                            currentNumber *= nextNumber;
                            break;
                        case '/':
                            // 나눗셈: 현재 값을 다음 숫자로 나눔
                            currentNumber /= nextNumber;
                            break;
                        case '+':
                            // 덧셈: 현재까지의 값을 결과에 더하고, 다음 숫자를 현재 값으로 설정
                            finalResult += currentNumber;
                            currentNumber = nextNumber;
                            break;
                        case '-':
                            // 뺄셈: 현재까지의 값을 결과에 더하고, 다음 숫자의 음수를 현재 값으로 설정
                            finalResult += currentNumber;
                            currentNumber = -nextNumber;
                            break;
                    }
                }
                // 현재 연산자를 저장 (다음 계산을 위해)
                operation = currentChar;
            }
        }

        // 마지막으로 남은 현재 값을 최종 결과에 더함
        finalResult += currentNumber;

        // 소수점 3자리에서 버림 처리하여 반환
        // Math.floor(x * 100) / 100.0 을 사용하여 소수점 2자리까지 표현
        return Math.floor(finalResult * 100) / 100.0;
    }
}
