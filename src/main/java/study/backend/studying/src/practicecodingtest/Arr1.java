// 문제 : 영단어와 숫자의 매핑
// https://school.programmers.co.kr/learn/courses/30/lessons/81301

/*
문제 해결 과정:
1. 문제 분석
  - 숫자의 일부 자릿수가 영단어로 바뀌어진 문자열 s가 주어짐
  - 원래 숫자를 찾아야 함
  - 예: "one4seveneight" → 1478
  - 예: "23four5six7" → 234567

2. 해결 방법 구상
  - 영단어와 숫자의 매핑 필요
  - 배열의 인덱스를 활용하여 영단어와 숫자를 매핑
  - 문자열의 replace 메소드를 활용하여 영단어를 숫자로 변환

3. 의사코드:
  함수 solution(문자열 s):
      영단어 배열 생성

      배열의 각 인덱스 i에 대해:
          영단어를 해당 인덱스(숫자)로 변환

      최종 문자열을 정수로 변환하여 반환
*/

package study.backend.studying.src.practicecodingtest;

public class Arr1 {
    public int solution(String s) {
        // 영단어 배열 생성
        // 배열의 인덱스가 숫자를 의미함
        // 예: words[0] = "zero", words[1] = "one", ...
        String[] words = {"zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine"};

        // 각 영단어를 해당하는 숫자로 변환
        for(int i = 0; i < words.length; i++) {
            // words[i]: 현재 처리할 영단어
            // String.valueOf(i): 인덱스를 문자열로 변환 (영단어에 대응되는 숫자)
            // replace: 문자열 내의 모든 영단어를 해당 숫자로 변환
            s = s.replace(words[i], String.valueOf(i));
        }

        // 최종적으로 완성된 숫자 문자열을 정수로 변환하여 반환
        return Integer.parseInt(s);
    }
}

/*
시간 복잡도 분석:
- words 배열을 순회: O(10) = O(1)
- 문자열 replace 연산: O(n) (n은 문자열 길이)
- 전체 시간 복잡도: O(n)

공간 복잡도 분석:
- words 배열: O(1) (고정 크기)
- 문자열 처리를 위한 임시 공간: O(n)
- 전체 공간 복잡도: O(n)

처리 과정 예시:
1. "one4seveneight"
  - "one" → "1" ("14seveneight")
  - "seven" → "7" ("147eight")
  - "eight" → "8" ("1478")
  - 최종 반환값: 1478

2. "23four5six7"
  - "four" → "4" ("234five7")
  - "five" → "5" ("2345six7")
  - "six" → "6" ("234567")
  - 최종 반환값: 234567

장점:
1. 코드가 간단하고 직관적
2. 배열 인덱스를 활용한 효율적인 매핑
3. String 클래스의 replace 메소드를 활용한 간편한 문자열 처리
*/
