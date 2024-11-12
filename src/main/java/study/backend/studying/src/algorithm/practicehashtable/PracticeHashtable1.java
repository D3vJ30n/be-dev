package study.backend.studying.src.algorithm.practicehashtable;// Practice1
// 해시 테이블을 이용한 수 찾기
// 주어진 첫 번째 배열을 이용하여 해시 테이블을 초기화 한 후
// 두 번째 배열이 주어졌을 때 해당 배열 내 데이터가 해시 테이블에 있는지 확인하는 코드를 작성하세요.

// 입출력 예시)
// 배열1: 1, 3, 5, 7, 9
// 배열2: 1, 2, 3, 4, 5
// 출력: True, False, True, False, True

import java.util.Hashtable;  // Java의 Hashtable 클래스 import

public class PracticeHashtable1 {
    // 문제 해결을 위한 메인 메서드
    // arr1: 기준이 되는 첫 번째 배열
    // arr2: 검색할 두 번째 배열
    public static void solution(int[] arr1, int[] arr2) {
        // 1. 해시테이블 생성
        // Hashtable<K, V>: K는 키의 타입, V는 값의 타입
        // 여기서는 배열의 값을 키와 값 모두로 사용 (키와 값이 동일)
        Hashtable<Integer, Integer> ht = new Hashtable<>();

        // 2. 첫 번째 배열로 해시테이블 초기화
        // arr1의 모든 원소를 해시테이블에 저장
        for (int i = 0; i < arr1.length; i++) {
            // put(key, value): 해시테이블에 키-값 쌍을 저장
            // 여기서는 배열의 값을 키로도 사용하고 값으로도 사용
            ht.put(arr1[i], arr1[i]);
        }

        // 3. 두 번째 배열의 각 원소에 대해 검사
        for (int i = 0; i < arr2.length; i++) {
            // containsKey(key): 해시테이블에 해당 키가 존재하는지 확인
            // 시간 복잡도 O(1)로 검색 가능
            if(ht.containsKey(arr2[i])) {
                System.out.println("True ");   // 존재하면 True 출력
            } else {
                System.out.println("False ");  // 존재하지 않으면 False 출력
            }
        }
        System.out.println();  // 줄바꿈
    }

    // 프로그램의 시작점
    public static void main(String[] args) {
        // 테스트를 위한 코드
        int[] arr1 = {1, 3, 5, 7, 9};     // 기준 배열
        int[] arr2 = {1, 2, 3, 4, 5};     // 검색할 배열
        solution(arr1, arr2);              // solution 메서드 호출
    }
}