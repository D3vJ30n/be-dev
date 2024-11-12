package study.backend.studying.src.algorithm;

// MyHashTable2 클래스는 이전에 만든 MyHashTable 클래스를 상속받아서 확장합니다.
// 해시 충돌을 해결하기 위해 선형 탐사법(Linear Probing)을 구현합니다.
//
// 선형 탐사법이란?
// - 충돌이 발생하면 그 다음 인덱스를 확인하여 비어있는 공간을 찾는 방법
// - 예: 인덱스 1에서 충돌 발생 → 2 확인 → 3 확인 → ... 빈 공간이 나올 때까지 계속
class MyHashTable2 extends MyHashTable {

    // 생성자
    // size: 해시테이블의 크기
    // super(size)는 부모 클래스(MyHashTable)의 생성자를 호출
    MyHashTable2(int size) {
        super(size);
    }

    // 데이터 저장 메서드를 새롭게 구현 (오버라이딩)
    // key: 저장할 데이터의 키
    // data: 실제 저장할 데이터 값
    public void setValue(int key, int data) {
        // 1. 해시 함수로 초기 저장 위치(인덱스) 계산
        int idx = this.getHash(key);

        // 2. 테이블이 가득 찼는지 확인
        if (this.elemCnt == this.table.length) {
            System.out.println("Hash table is full!"); // 가득 찼다는 메시지 출력
            return;                                    // 메서드 종료
        }
        // 3. 계산된 인덱스가 비어있는 경우
        else if (this.table[idx] == null) {
            this.table[idx] = data;     // 바로 데이터 저장
        }
        // 4. 계산된 인덱스에 이미 데이터가 있는 경우 (충돌 발생!)
        else {
            int newIdx = idx;   // 새로운 인덱스 변수 생성

            // 빈 공간을 찾을 때까지 반복
            while (true) {
                // 다음 인덱스 계산
                // (newIdx + 1) % this.table.length는 배열의 끝에 도달하면
                // 다시 처음으로 돌아가도록 하는 순환 구조
                newIdx = (newIdx + 1) % this.table.length;

                // 빈 공간을 찾았다면 반복 종료
                if (this.table[newIdx] == null) {
                    break;
                }
            }
            // 찾은 빈 공간에 데이터 저장
            this.table[newIdx] = data;
        }

        // 저장된 데이터 개수 1 증가
        elemCnt++;
    }
}

// 메인 클래스: 해시테이블 테스트
public class Hashtable3 {
    public static void main(String[] args) {
        // 테스트 코드

        // 1. 크기가 5인 해시테이블 생성
        MyHashTable2 ht = new MyHashTable2(5);

        // 2. 첫 번째 테스트: 기본 저장
        ht.setValue(1, 1);    // 키:1, 값:1 → 인덱스:1(1%5=1)에 저장
        ht.setValue(3, 3);    // 키:3, 값:3 → 인덱스:3(3%5=3)에 저장
        ht.printHashTable();  // 현재 상태 출력

        // 3. 두 번째 테스트: 충돌 발생
        ht.setValue(1, 10);   // 키:1, 값:10 → 인덱스:1은 이미 차있음
        // → 다음 빈 공간(인덱스:2)에 저장
        ht.printHashTable();  // 현재 상태 출력

        // 4. 세 번째 테스트: 연속된 충돌 발생
        ht.setValue(1, 20);   // 키:1 → 인덱스:1 차있음 → 2 차있음 → 4에 저장
        ht.setValue(1, 30);   // 키:1 → 인덱스:1 차있음 → 2 차있음 → 4 차있음 → 0에 저장
        ht.setValue(1, 40);   // 키:1 → 테이블이 가득 찼으므로 저장 실패
        ht.printHashTable();  // 최종 상태 출력
    }
}