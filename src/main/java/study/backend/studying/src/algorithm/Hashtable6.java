package study.backend.studying.src.algorithm;

// 노드 클래스: 연결 리스트에서 사용할 각각의 노드를 정의
// 각 노드는 키, 데이터, 다음 노드 참조를 가짐
class Node {
    int key;     // 키 값 (데이터를 찾을 때 사용)
    int data;    // 실제 저장할 데이터
    Node next;   // 다음 노드를 가리키는 참조 변수

    // 기본 생성자
    Node() {}

    // 매개변수가 있는 생성자
    Node(int key, int data, Node next) {
        this.key = key;     // 키 설정
        this.data = data;   // 데이터 설정
        this.next = next;   // 다음 노드 참조 설정
    }
}

// 연결 리스트 클래스: 노드들을 연결하여 데이터를 저장
class LinkedList {
    Node head;   // 연결 리스트의 시작 노드

    // 기본 생성자
    LinkedList() {}

    // 시작 노드를 지정하는 생성자
    LinkedList(Node node) {
        this.head = node;
    }

    // 연결 리스트가 비어있는지 확인하는 메서드
    public boolean isEmpty() {
        if (this.head == null) {
            return true;    // head가 null이면 비어있음
        }
        return false;      // 아니면 데이터가 있음
    }

    // 새로운 데이터를 연결 리스트 끝에 추가하는 메서드
    public void addData(int key, int data) {
        // 리스트가 비어있는 경우
        if (this.head == null) {
            this.head = new Node(key, data, null);  // 새 노드를 head로 설정
        }
        // 리스트에 데이터가 있는 경우
        else {
            Node cur = this.head;
            // 마지막 노드까지 이동
            while (cur.next != null) {
                cur = cur.next;
            }
            // 마지막 노드의 다음으로 새 노드 추가
            cur.next = new Node(key, data, null);
        }
    }

    // 특정 키를 가진 노드를 삭제하는 메서드
    public void removeData(int data) {
        // 리스트가 비어있는 경우
        if (this.isEmpty()) {
            System.out.println("List is empty");
            return;
        }

        Node cur = this.head;  // 현재 노드
        Node pre = cur;        // 이전 노드

        // 리스트를 순회하면서 삭제할 노드 찾기
        while (cur != null) {
            if (cur.key == data) {
                // head를 삭제하는 경우
                if (cur == this.head) {
                    this.head = cur.next;
                }
                // 중간이나 끝의 노드를 삭제하는 경우
                else {
                    pre.next = cur.next;
                }
                break;
            }
            // 다음 노드로 이동
            pre = cur;
            cur = cur.next;
        }
    }

    // 특정 키를 가진 노드의 데이터를 찾는 메서드
    public Integer findData(int key) {
        // 리스트가 비어있는 경우
        if (this.isEmpty()) {
            System.out.println("List is empty");
            return null;
        }

        Node cur = this.head;
        // 리스트를 순회하면서 키를 찾음
        while (cur != null) {
            if (cur.key == key) {
                return cur.data;  // 키를 찾으면 해당 데이터 반환
            }
            cur = cur.next;
        }
        System.out.println("Data not found!");
        return null;
    }

    // 연결 리스트의 모든 데이터를 출력하는 메서드
    public void showData() {
        if (this.isEmpty()) {
            System.out.println("List is empty!");
            return;
        }

        Node cur = this.head;
        // 모든 노드를 순회하면서 데이터 출력
        while (cur != null) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }
}

// 분리 연결법을 사용한 해시테이블 클래스
class MyHashTable5 {
    LinkedList[] table;    // 연결 리스트 배열로 해시테이블 구현

    // 생성자
    MyHashTable5(int size) {
        this.table = new LinkedList[size];  // size 크기의 연결 리스트 배열 생성
        // 각 배열 요소를 빈 연결 리스트로 초기화
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new LinkedList(null);
        }
    }

    // 해시 함수: 키를 해시테이블의 인덱스로 변환
    public int getHash(int key) {
        return key % this.table.length;
    }

    // 데이터 저장 메서드
    public void setValue(int key, int data) {
        int idx = this.getHash(key);    // 해시 함수로 인덱스 계산
        this.table[idx].addData(key, data);  // 해당 인덱스의 연결 리스트에 데이터 추가
    }

    // 데이터 조회 메서드
    public int getValue(int key) {
        int idx = this.getHash(key);    // 해시 함수로 인덱스 계산
        int data = this.table[idx].findData(key);  // 해당 연결 리스트에서 데이터 찾기
        return data;
    }

    // 데이터 삭제 메서드
    public void removeValue(int key) {
        int idx = this.getHash(key);    // 해시 함수로 인덱스 계산
        this.table[idx].removeData(key);  // 해당 연결 리스트에서 데이터 삭제
    }

    // 해시테이블 전체 출력 메서드
    public void printHashTable() {
        System.out.println("== Hash Table ==");
        for (int i = 0; i < this.table.length; i++) {
            System.out.print(i + ": ");  // 인덱스 출력
            this.table[i].showData();    // 각 연결 리스트의 데이터 출력
        }
    }
}

// 메인 클래스: 해시테이블 테스트
public class Hashtable6 {
    public static void main(String[] args) {
        // 테스트 코드
        MyHashTable5 ht = new MyHashTable5(11);  // 크기가 11인 해시테이블 생성

        // 데이터 저장 테스트
        ht.setValue(1, 10);   // 인덱스 1
        ht.setValue(2, 20);   // 인덱스 2
        ht.setValue(3, 30);   // 인덱스 3
        ht.printHashTable();

        // 충돌 테스트 (같은 인덱스에 여러 데이터 저장)
        ht.setValue(12, 11);  // 인덱스 1 (12 % 11 = 1)
        ht.setValue(23, 12);  // 인덱스 1 (23 % 11 = 1)
        ht.setValue(34, 13);  // 인덱스 1 (34 % 11 = 1)

        ht.setValue(13, 21);  // 인덱스 2
        ht.setValue(24, 22);  // 인덱스 2
        ht.setValue(35, 23);  // 인덱스 2

        ht.setValue(5, 1);    // 인덱스 5
        ht.setValue(16, 2);   // 인덱스 5
        ht.setValue(27, 3);   // 인덱스 5
        ht.printHashTable();

        // 데이터 조회 테스트
        System.out.println("== key 값으로 해당 데이터 가져오기 ==");
        System.out.println(ht.getValue(1));   // 키 1의 데이터 조회
        System.out.println(ht.getValue(12));  // 키 12의 데이터 조회

        // 데이터 삭제 테스트
        System.out.println("== 데이터 삭제 ==");
        ht.removeValue(1);    // 키 1의 데이터 삭제
        ht.removeValue(5);    // 키 5의 데이터 삭제
        ht.removeValue(16);   // 키 16의 데이터 삭제
        ht.printHashTable();
    }
}