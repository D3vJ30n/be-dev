package study.backend.studying.src.algorithm; // 이 코드가 algorithm 패키지에 속한다는 것을 알려줌

// MyHashTable 클래스 정의
// 해시테이블이란? 키(key)를 값(value)에 매핑하는 자료구조입니다.
// 예: 학번(키)으로 학생 정보(값)를 찾는 것과 비슷합니다.
class MyHashTable {
    // 클래스의 멤버 변수들
    Integer[] table;    // 실제 데이터를 저장할 배열
    // Integer는 int의 클래스 버전으로, null을 저장할 수 있음

    int elemCnt;       // 현재 저장된 데이터의 개수를 세는 변수
    // 0부터 시작해서 데이터를 넣을 때마다 1씩 증가

    // 기본 생성자 - 아무 인자도 받지 않는 생성자
    MyHashTable() {}

    // 크기를 지정하는 생성자
    // size 매개변수: 해시테이블의 크기를 지정
    MyHashTable(int size) {
        // size 크기의 Integer 배열을 생성
        // 예: size가 7이면 7개의 데이터를 저장할 수 있는 배열이 생성됨
        this.table = new Integer[size];

        // 저장된 데이터 개수를 0으로 초기화
        this.elemCnt = 0;
    }

    // 해시 함수: 키를 배열의 인덱스로 변환하는 함수
    // 예: key가 8이고 배열 크기가 7이면, 8 % 7 = 1이 반환됨
    public int getHash(int key) {
        // % 연산자: 나눗셈의 나머지를 구함
        // table.length는 배열의 크기
        return key % this.table.length;
    }

    // 데이터를 저장하는 메서드
    // key: 데이터를 찾을 때 사용할 키 값
    // data: 실제로 저장할 데이터
    public void setValue(int key, int data) {
        // 1. 해시 함수로 저장할 위치(인덱스) 계산
        int idx = this.getHash(key);

        // 2. 계산된 인덱스에 데이터 저장
        this.table[idx] = data;

        // 3. 저장된 데이터 개수 1 증가
        this.elemCnt++;
    }

    // 데이터를 가져오는 메서드
    // key: 찾고 싶은 데이터의 키 값
    public int getValue(int key) {
        // 1. 해시 함수로 데이터가 저장된 위치(인덱스) 계산
        int idx = this.getHash(key);

        // 2. 그 위치에 있는 데이터 반환
        return this.table[idx];
    }

    // 데이터를 삭제하는 메서드
    // key: 삭제하고 싶은 데이터의 키 값
    public void removeValue(int key) {
        // 1. 해시 함수로 삭제할 데이터의 위치(인덱스) 계산
        int idx = this.getHash(key);

        // 2. 해당 위치의 데이터를 null로 변경 (데이터 삭제)
        this.table[idx] = null;

        // 3. 저장된 데이터 개수 1 감소
        this.elemCnt--;
    }

    // 해시테이블의 내용을 출력하는 메서드
    public void printHashTable() {
        System.out.println("== Hash Table ==");
        // 배열의 모든 위치(인덱스)를 순서대로 확인
        for (int i = 0; i < this.table.length; i++) {
            // 각 인덱스와 그 위치에 저장된 데이터를 출력
            // 형식: 인덱스: 데이터
            System.out.println(i + ": " + this.table[i]);
        }
    }
}

// 실제로 해시테이블을 테스트하는 메인 클래스
public class Hashtable2 {
    public static void main(String[] args) {
        // 테스트 코드

        // 1. 크기가 7인 해시테이블 생성
        MyHashTable ht = new MyHashTable(7);

        // 2. 데이터 저장 테스트
        // setValue(키, 값) 형태로 데이터 저장
        ht.setValue(1, 1);  // 키:1 → 인덱스:1(1%7=1)에 값:1 저장
        ht.setValue(2, 2);  // 키:2 → 인덱스:2(2%7=2)에 값:2 저장
        ht.setValue(3, 3);  // 키:3 → 인덱스:3(3%7=3)에 값:3 저장
        ht.setValue(4, 4);  // 키:4 → 인덱스:4(4%7=4)에 값:4 저장
        ht.setValue(5, 5);  // 키:5 → 인덱스:5(5%7=5)에 값:5 저장

        // 3. 현재 해시테이블 상태 출력
        ht.printHashTable();

        // 4. 충돌 상황 테스트
        // 키:8을 넣으면 인덱스는 8%7=1이 됨
        // 그런데 인덱스 1에는 이미 다른 값이 있음! (충돌 발생)
        ht.setValue(8, 6);  // 키:8 → 인덱스:1(8%7=1)에 값:6 저장
        // 인덱스 1의 기존 값(1)이 새로운 값(6)으로 덮어씌워짐

        // 5. 충돌 후 해시테이블 상태 출력
        ht.printHashTable();
    }
}