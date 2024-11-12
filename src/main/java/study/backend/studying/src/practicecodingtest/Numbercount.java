// 문제 : https://www.acmicpc.net/problem/10807

/*
문제 해결 과정:
1. 입력값 받기 (N, N개의 정수, 찾을 값 v)
2. 배열을 순회하면서 v와 같은 값의 개수 세기
3. 결과 출력

/*
의사코드 (한글):
1. 정수의 개수 N을 입력받는다
2. 크기가 N인 정수 배열을 생성한다
3. N번 반복하면서:
    3.1 정수를 입력받아 배열에 저장한다
4. 찾으려는 정수 v를 입력받는다
5. 카운트 변수를 0으로 초기화한다
6. 배열의 모든 원소에 대해 반복:
    6.1 현재 원소가 v와 같다면:
        6.1.1 카운트를 1 증가시킨다
7. 카운트를 출력한다
*/

package study.backend.studying.src.practicecodingtest;

import java.util.Scanner;

public class Numbercount {

    public static void main(String[] args) {
        // Scanner 클래스: 입력을 받기 위한 자바의 기본 클래스
        Scanner sc = new Scanner(System.in);

        // nextInt(): 다음 정수를 읽어옴
        // N의 범위: 1 ≤ N ≤ 100
        int N = sc.nextInt();

        // 배열 선언 및 초기화
        // new int[N]: N개의 정수를 저장할 수 있는 배열 생성
        int[] numbers = new int[N];

        // 배열에 N개의 정수 저장
        // 입력되는 정수 범위: -100 ≤ 정수 ≤ 100
        for(int i = 0; i < N; i++) {
            numbers[i] = sc.nextInt();
        }

        // 찾으려는 정수 v 입력
        // v의 범위: -100 ≤ v ≤ 100
        int v = sc.nextInt();

        // v의 등장 횟수를 저장할 변수
        int count = 0;

        // 향상된 for문(enhanced for loop)을 사용하여 배열 순회
        // for(자료형 변수명 : 배열) 형태로 사용
        // 배열의 각 요소를 차례대로 number에 대입
        for(int number : numbers) {
            // 현재 숫자가 v와 같다면 카운트 증가
            if(number == v) {
                count++;
            }
        }

        // 최종 결과 출력
        System.out.println(count);

        // Scanner 객체 닫기 (리소스 해제)
        sc.close();
    }
}

/*
주요 문법 설명:
1. Scanner
   - java.util 패키지에 포함된 입력 클래스
   - System.in을 통해 키보드 입력을 받음

2. 배열 선언
   - int[] numbers = new int[N]
   - 크기가 N인 정수 배열 생성

3. for 반복문
   - 기본 for문: for(초기값; 조건; 증감)
   - 향상된 for문: for(자료형 변수 : 배열)

4. if 조건문
   - if(조건) { 실행문 }
   - 조건이 true일 때 실행문 수행

시간 복잡도: O(N)
- N개의 숫자를 한 번씩 확인

공간 복잡도: O(N)
- N개의 숫자를 저장하는 배열 사용

주의사항:
1. 입력값의 범위 확인 (-100 ≤ 정수 ≤ 100)
2. Scanner 사용 후 닫기
3. 배열 인덱스 범위 주의 (0 ~ N-1)
*/