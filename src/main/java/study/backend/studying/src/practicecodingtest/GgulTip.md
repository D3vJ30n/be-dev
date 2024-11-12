# Java 기본 수학 계산 레퍼런스

## 1. 기본적인 수 다루기

### 홀수/짝수 판별
```java
if (num % 2 == 1)    // 홀수
if (num % 2 == 0)    // 짝수
```

### n 이하의 개수 구하기
```java
int oddCount = (n + 1) / 2;    // 홀수 개수
int evenCount = n / 2;         // 짝수 개수
```

### n번째 수 구하기
```java
int nthOdd = 2 * n - 1;    // n번째 홀수
int nthEven = 2 * n;       // n번째 짝수
```

### 평균 구하기
```java
double average = sum / count;           // 정수 나눗셈
double average = (double)sum / count;   // 실수 나눗셈
```

## 2. Math 클래스 메소드

### 올림, 내림, 반올림
```java
Math.ceil(3.14);     // 4.0  (올림)
Math.floor(3.14);    // 3.0  (내림)
Math.round(3.14);    // 3    (반올림)
```

### 최대값, 최소값
```java
Math.max(5, 10);     // 10   (큰 값)
Math.min(5, 10);     // 5    (작은 값)
```

### 절대값
```java
Math.abs(-5);        // 5    (절대값)
```

### 제곱, 제곱근
```java
Math.pow(2, 3);      // 8.0  (2의 3제곱)
Math.sqrt(16);       // 4.0  (루트 16)
```

## 3. 형변환

### 문자열 ↔ 숫자
```java
String.valueOf(123);         // "123" (숫자를 문자열로)
Integer.parseInt("123");     // 123   (문자열을 정수로)
Double.parseDouble("3.14");  // 3.14  (문자열을 실수로)
```

### 문자 ↔ 숫자
```java
Character.getNumericValue('9');    // 9     (문자를 숫자로)
char ch = (char)(5 + '0');         // '5'   (숫자를 문자로)
```

## 4. 자주 사용되는 계산식

### 자릿수 구하기
```java
int digits = (int)Math.log10(number) + 1;
```

### 거듭제곱 확인
```java
boolean isPowerOfTwo = (n & (n-1)) == 0;    // 2의 거듭제곱 확인
```

### 최대공약수(GCD)
```java
public static int gcd(int a, int b) {
    while(b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
```

### 최소공배수(LCM)
```java
public static int lcm(int a, int b) {
    return a * b / gcd(a, b);
}
```

## 5. 배열 관련 계산

### 배열의 합
```java
int sum = Arrays.stream(arr).sum();
```

### 평균
```java
double avg = Arrays.stream(arr).average().orElse(0);
```

### 최대값, 최소값
```java
int max = Arrays.stream(arr).max().orElse(0);
int min = Arrays.stream(arr).min().orElse(0);
```

## 주의사항

1. 나눗셈 연산 시 0으로 나누는 경우 체크
2. 형변환 시 데이터 손실 주의
3. 오버플로우/언더플로우 가능성 체크
4. 실수 연산 시 부동소수점 오차 고려