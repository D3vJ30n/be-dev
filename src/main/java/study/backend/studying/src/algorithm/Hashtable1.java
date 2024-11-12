package study.backend.studying.src.algorithm; // 패키지 선언: 이 클래스가 algorithm 패키지에 속함을 나타냄

// 필요한 Java 클래스들을 import
import java.util.Hashtable; // 해시테이블 클래스를 사용하기 위한 import
import java.util.Map; // Map 인터페이스를 사용하기 위한 import

// Hashtable1 클래스 정의
public class Hashtable1 {
    // 해시 함수 정의
    // 해시 함수란? 입력값을 특정 범위의 숫자로 변환해주는 함수입니다.
    // 여기서는 입력된 숫자를 5로 나눈 나머지를 반환합니다.
    // 예: 1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4, 5 -> 0, 6 -> 1, 7 -> 2, ...
    public static int getHash(int key) {
        return key % 5; // % 연산자는 나머지를 구하는 연산자입니다.
    }

    // 메인 메소드: 프로그램의 시작점
    public static void main(String[] args) {
        // =========== 1. 기본적인 해시테이블 사용법 ===========

        // Hashtable 객체 생성
        // <String, Integer>는 키는 문자열, 값은 정수를 사용하겠다는 의미
        Hashtable<String, Integer> ht = new Hashtable();

        // put() 메소드: 해시테이블에 데이터를 저장하는 메소드
        // put(키, 값) 형태로 사용
        ht.put("key1", 10); // "key1"이라는 키에 10이라는 값을 저장
        ht.put("key2", 20); // "key2"이라는 키에 20이라는 값을 저장
        ht.put("key3", 30); // "key3"이라는 키에 30이라는 값을 저장
        // ht.put("key3", 40);
        // 위 줄의 주석을 해제하면, 같은 키에 새로운 값을 넣으므로
        // key3의 값이 30에서 40으로 덮어씌워집니다.

        // 해시테이블의 모든 데이터를 순회하면서 출력
        // entrySet(): 해시테이블의 모든 키-값 쌍을 가져옴
        System.out.println("=== 초기 해시테이블 내용 ===");
        for (Map.Entry<String, Integer> item: ht.entrySet()) {
            // getKey(): 키 값을 가져옴
            // getValue(): 값을 가져옴
            System.out.println(item.getKey() + " - " + item.getValue());
        }

        // get() 메소드: 특정 키의 값을 조회하는 메소드
        System.out.println("\n=== 특정 키의 값 조회 ===");
        System.out.println("key1의 값: " + ht.get("key1")); // key1에 해당하는 값 10 출력
        System.out.println("key2의 값: " + ht.get("key2")); // key2에 해당하는 값 20 출력

        // remove() 메소드: 특정 키-값 쌍을 삭제하는 메소드
        ht.remove("key1"); // key1과 그에 해당하는 값을 삭제

        System.out.println("\n=== key1 삭제 후 해시테이블 내용 ===");
        for (Map.Entry<String, Integer> item: ht.entrySet()) {
            System.out.println(item.getKey() + " - " + item.getValue());
        }

        // =========== 2. 해시 충돌 예제 ===========

        // 새로운 해시테이블 생성 (키와 값 모두 Integer 타입)
        Hashtable<Integer, Integer> ht2 = new Hashtable();

        // getHash() 함수를 사용하여 키를 생성하고 값을 저장
        // 아래 각 줄에서 어떤 키가 생성되는지 확인해보세요
        ht2.put(getHash(1), 10); // 1 % 5 = 1 -> 키:1, 값:10
        ht2.put(getHash(2), 20); // 2 % 5 = 2 -> 키:2, 값:20
        ht2.put(getHash(3), 30); // 3 % 5 = 3 -> 키:3, 값:30
        ht2.put(getHash(4), 40); // 4 % 5 = 4 -> 키:4, 값:40
        ht2.put(getHash(5), 50); // 5 % 5 = 0 -> 키:0, 값:50

        // 충돌 발생 전의 해시테이블 내용 출력
        System.out.println("\n=== 충돌 발생 전 해시테이블 내용 ===");
        for (Map.Entry<Integer, Integer> item: ht2.entrySet()) {
            System.out.println("키:" + item.getKey() + " - 값:" + item.getValue());
        }

        // 해시 충돌 발생 케이스
        // getHash(6)은 1을 반환 (6 % 5 = 1)
        // 그런데 키가 1인 항목이 이미 있으므로 충돌이 발생!
        // Java의 Hashtable은 충돌이 발생하면 새로운 값으로 덮어씌움
        System.out.println("\n=== 충돌 발생 후 해시테이블 내용 ===");
        ht2.put(getHash(6), 60); // 6 % 5 = 1 -> 키:1의 값이 60으로 덮어씌워짐
        for (Map.Entry<Integer, Integer> item: ht2.entrySet()) {
            System.out.println("키:" + item.getKey() + " - 값:" + item.getValue());
        }
    }
}