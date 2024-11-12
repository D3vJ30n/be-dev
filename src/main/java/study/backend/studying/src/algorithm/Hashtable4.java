package study.backend.studying.src.algorithm;

// MyHashTable3 클래스: 제곱 탐사법으로 해시 충돌을 해결하는 해시테이블 구현
//
// 제곱 탐사법이란?
// - 충돌이 발생했을 때, 1², 2², 3², ... 만큼 건너뛰면서 빈 공간을 찾는 방법
// - 예: 충돌 발생 시 순서대로 1, 4, 9, 16, ... 칸을 이동하여 확인
// - 선형 탐사법의 군집화 문제를 완화하기 위해 사용
class MyHashTable3 extends MyHashTable {

    // 생성자
    // size: 해시테이블의 크기
    // super(size)로 부모 클래스의 생성자 호출
    MyHashTable3(int size) {
        super(size);
    }

    // 데이터 저장 메서드
    // key: 저장할 데이터의 키
    // data: 실제 저장할 데이터 값
    public void setValue(int key, int data) {
        // 1. 해시 함수로 초기 저장 위치(인덱스) 계산
        int idx = this.getHash(key);

        // 2. 테이블이 가득 찼는지 확인
        if (this.elemCnt == this.table.length) {
            System.out.println("Hash table is full!");  // 가득 찼다는 메시지 출력
            return;                                     // 메서드 종료
        }
        // 3. 계산된 인덱스가 비어있는 경우
        else if (this.table[idx] == null) {
            this.table[idx] = data;      // 바로 데이터 저장
        }
        // 4. 계산된 인덱스에 이미 데이터가 있는 경우 (충돌 발생!)
        else {
            int newIdx = idx;    // 새로운 인덱스 변수
            int cnt = 0;         // 시도 횟수 카운터 (제곱수를 계산하는데 사용)

            // 빈 공간을 찾을 때까지 반복
            while (true) {
                // 다음 위치 계산
                // Math.pow(2, cnt)는 2의 cnt제곱을 계산 (1, 4, 9, 16, ...)
                // (int)로 형변환하여 소수점 제거
                // %연산자로 배열 범위 내에서 순환되도록 함
                newIdx = (newIdx + (int)Math.pow(2, cnt)) % this.table.length;

                // 빈 공간을 찾았다면 반복 종료
                if (this.table[newIdx] == null) {
                    break;
                }
                cnt++;    // 시도 횟수 증가
            }
            // 찾은 빈 공간에 데이터 저장
            this.table[newIdx] = data;
        }

        // 저장된 데이터 개수 1 증가
        elemCnt++;
    }
}

// 메인 클래스: 해시테이블 테스트
public class Hashtable4 {
    public static void main(String[] args) {
        // 테스트 코드

        // 1. 크기가 11인 해시테이블 생성
        MyHashTable3 ht = new MyHashTable3(11);

        // 2. 초기 데이터 저장
        ht.setValue(1, 10);    // 키:1, 값:10 → 인덱스:1(1%11=1)에 저장
        ht.setValue(2, 20);    // 키:2, 값:20 → 인덱스:2(2%11=2)에 저장
        ht.setValue(4, 40);    // 키:4, 값:40 → 인덱스:4(4%11=4)에 저장
        ht.printHashTable();   // 현재 상태 출력

        // 3. 첫 번째 충돌 테스트
        ht.setValue(1, 100);   // 키:1 → 인덱스:1은 이미 차있음
        // → 1칸 이동한 인덱스:2도 차있음
        // → 4칸 이동한 인덱스:5에 저장
        ht.printHashTable();   // 현재 상태 출력

        // 4. 연속된 충돌 테스트
        ht.setValue(1, 200);   // 키:1 → 인덱스:1 차있음 → +1 → 2 차있음 → +4 → 5 차있음 → +9 → ...
        ht.setValue(1, 300);
        ht.setValue(1, 400);
        ht.printHashTable();   // 최종 상태 출력
    }
}