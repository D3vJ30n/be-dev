//package study.backend.studying.src.algorithm.practicehashtable;
//
//// Hashtable과 HashMap의 차이점을 설명하는 예제 코드
//// Map 인터페이스를 구현한 두 클래스의 특징을 비교
//
//import java.util.HashMap;      // HashMap 클래스 import
//import java.util.Hashtable;    // Hashtable 클래스 import
//import java.util.Map;          // Map 인터페이스 import
//
//public class PracticeHashtable3 {
//    public static void main(String[] args) {
//        // 1. Hashtable 사용 예제
//        // Hashtable은 동기화(synchronized)되어 있어 스레드에 안전
//        Hashtable<Integer, Integer> ht = new Hashtable<>();
//        ht.put(0, 10);    // 키:0, 값:10 저장
//        System.out.println("Hashtable 값: " + ht.get(0));  // 저장된 값 출력
//
//        // 2. HashMap 사용 예제
//        // HashMap은 동기화되어 있지 않아 단일 스레드에서 성능이 좋음
//        HashMap<Integer, Integer> hm = new HashMap<>();
//        hm.put(0, 10);    // 키:0, 값:10 저장
//        System.out.println("HashMap 값: " + hm.get(0));    // 저장된 값 출력
//
//        // 3. Map 인터페이스를 이용한 다형성 예제
//        // Map은 Hashtable과 HashMap의 상위 인터페이스
//        Map<Integer, Integer> map1 = ht;  // Hashtable을 Map으로 참조
//        Map<Integer, Integer> map2 = hm;  // HashMap을 Map으로 참조
//        System.out.println("Map1(Hashtable) 값: " + map1.get(0));
//        System.out.println("Map2(HashMap) 값: " + map2.get(0));
//
//        // 4. null 값 테스트
//        // Hashtable은 null을 키나 값으로 사용할 수 없음
////        ht.put(null, -999);  // 에러 발생! NullPointerException
////        System.out.println(ht.get(null));
//
//        // HashMap은 null을 키나 값으로 사용 가능
//        hm.put(null, -999);    // 정상 작동
//        System.out.println("HashMap의 null 키 값: " + hm.get(null));
//
//        // === Hashtable과 HashMap의 주요 차이점 ===
//
//        // 1. Thread-Safe (스레드 안전성)
//        //    - Hashtable:
//        //      * 동기화(synchronized) 되어 있음
//        //      * 멀티스레드 환경에서 안전
//        //      * 하나의 스레드가 사용중이면 다른 스레드는 대기
//        //      * 성능은 상대적으로 느림
//
//        //    - HashMap:
//        //      * 동기화되어 있지 않음
//        //      * 단일 스레드 환경에서 사용하기 적합
//        //      * 동시 접근이 가능하여 성능이 빠름
//        //      * 멀티스레드 환경에서는 별도의 동기화 처리 필요
//
//        // 2. Null 값 허용
//        //    - Hashtable:
//        //      * 키와 값 모두 null 사용 불가
//        //      * null 삽입 시도시 NullPointerException 발생
//
//        //    - HashMap:
//        //      * 키와 값 모두 null 사용 가능
//        //      * 여러 개의 null 값은 가능하나, null 키는 하나만 가능
//
//        // 3. 성능
//        //    - Hashtable:
//        //      * 동기화로 인한 오버헤드 존재
//        //      * 상대적으로 느린 성능
//
//        //    - HashMap:
//        //      * 동기화 처리가 없어 빠른 성능
//        //      * 단일 스레드 환경에서 권장
//
//        // 참고) 멀티스레드 환경에서 HashMap을 안전하게 사용하는 방법
//        // 1. Collections.synchronizedMap(): HashMap을 동기화된 Map으로 래핑
//        // 2. ConcurrentHashMap: 동기화된 HashMap의 개선 버전
//        //    - 더 효율적인 동시성 처리
//        //    - 특정 세그먼트나 버킷만 잠금
//    }
//}