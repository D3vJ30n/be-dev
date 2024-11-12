package study.backend.studying.src.algorithm;

// MyHashTable4 클래스: 이중 해싱으로 해시 충돌을 해결하는 해시테이블 구현
//
// 이중 해싱이란?
// - 충돌이 발생했을 때, 두 번째 해시 함수를 사용하여 이동할 간격을 결정하는 방법
// - 다른 방법들과 달리 간격이 고정되지 않고 키에 따라 달라짐
// - 더 균등한 데이터 분포를 얻을 수 있음
class MyHashTable4 extends MyHashTable {
    int c;  // 두 번째 해시 함수에서 사용할 소수 값

    // 생성자
    // size: 해시테이블의 크기
    MyHashTable4(int size) {
        super(size);             // 부모 클래스 생성자 호출
        this.c = this.getHashC(size);  // 두 번째 해시 함수에서 사용할 소수 찾기
    }

    // size보다 작은 가장 큰 소수를 찾는 메서드
    // 이중 해싱의 효율을 높이기 위해 소수를 사용
    public int getHashC(int size) {
        int c = 0;

        // 크기가 2 이하면 그 값을 그대로 반환
        if (size <= 2) {
            return size;
        }

        // size-1부터 3까지 역순으로 확인하면서 소수 찾기
        for (int i = size - 1; i > 2; i--) {
            boolean isPrime = true;  // 소수 여부를 저장할 변수

            // 2부터 i-1까지 나누어보면서 소수인지 확인
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {    // 나누어 떨어지면 소수가 아님
                    isPrime = false;
                    break;
                }
            }

            // 소수를 찾으면 저장하고 반복 종료
            if (isPrime) {
                c = i;
                break;
            }
        }
        return c;
    }

    // 두 번째 해시 함수
    // 충돌이 발생했을 때 이동할 간격을 계산
    public int getHash2(int key) {
        // 1 + (key를 c로 나눈 나머지)
        // 1을 더하는 이유: 결과가 0이 되는 것을 방지
        int hash = 1 + key % this.c;
        return hash;
    }

    // 데이터 저장 메서드
    // key: 저장할 데이터의 키
    // data: 실제 저장할 데이터 값
    public void setValue(int key, int data) {
        // 1. 첫 번째 해시 함수로 초기 저장 위치 계산
        int idx = this.getHash(key);

        // 2. 테이블이 가득 찼는지 확인
        if (this.elemCnt == this.table.length) {
            System.out.println("Hash table is full!");
            return;
        }
        // 3. 계산된 인덱스가 비어있는 경우
        else if (this.table[idx] == null) {
            this.table[idx] = data;    // 바로 데이터 저장
        }
        // 4. 계산된 인덱스에 이미 데이터가 있는 경우 (충돌 발생!)
        else {
            int newIdx = idx;   // 새로운 인덱스 변수
            int cnt = 1;        // 시도 횟수

            // 빈 공간을 찾을 때까지 반복
            while (true) {
                // 새로운 인덱스 계산
                // newIdx: 현재 위치
                // getHash2(newIdx): 이동할 간격
                // cnt: 시도 횟수
                // 이동할 간격 = 두 번째 해시 함수의 결과 * 시도 횟수
                newIdx = (newIdx + this.getHash2(newIdx) * cnt) % this.table.length;

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
public class Hashtable5 {
    public static void main(String[] args) {
        // 테스트 코드

        // 1. 크기가 11인 해시테이블 생성
        MyHashTable4 ht = new MyHashTable4(11);

        // 2. 초기 데이터 저장
        ht.setValue(1, 10);    // 키:1, 값:10 저장
        ht.setValue(2, 20);    // 키:2, 값:20 저장
        ht.setValue(3, 30);    // 키:3, 값:30 저장
        ht.printHashTable();   // 현재 상태 출력

        // 3. 충돌 테스트
        ht.setValue(1, 100);   // 키:1로 충돌 발생 → 두 번째 해시 함수로 새 위치 계산
        ht.setValue(1, 200);   // 키:1로 충돌 발생
        ht.setValue(1, 300);   // 키:1로 충돌 발생
        ht.printHashTable();   // 최종 상태 출력
    }
}