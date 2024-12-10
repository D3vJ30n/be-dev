//package study.backend.studying.src.algorithm.practicehashtable;// Practice2
//// 정수형 배열 nums 와 target 이 주어졌을 때,
//// nums 에서 임의의 두 수를 더해 target 을 구할 수 있는지 확인하는 프로그램을 작성하세요.
//// 두 수 의 합으로 target 을 구할 수 있으면 해당 값의 index 를 반환하고,
//// 없는 경우 null 을 반환하세요.
//
//// 입출력 예시
//// nums: 7, 11, 5, 3
//// target: 10
//// 출력: 0, 3
//
//// nums: 8, 3, -2
//// target: 6
//// 출력: 0, 2
//
///*
//===== 의사 코드(Pseudocode) =====
//function solution(numbers, target)
//    // 1. 초기화
//    result = new array of size 2
//    ht = new HashTable
//
//    // 2. 배열 순회하면서 검사
//    for i = 0 to numbers.length-1 do
//        현재숫자 = numbers[i]
//        찾는숫자 = target - 현재숫자
//
//        if (현재숫자가 해시테이블의 키로 존재) then
//            result[0] = 해시테이블에서 현재숫자에 대한 값(인덱스) 가져오기
//            result[1] = 현재 인덱스(i)
//            return result
//        end if
//
//        해시테이블에 (찾는숫자, 현재인덱스) 저장
//    end for
//
//    return null  // 못 찾은 경우
//end function
//*/
//
//import java.util.Arrays;
//import java.util.Hashtable;
//
//public class PractiveHashtable2 {
//    public static int[] solution(int[] numbers, int target) {
//        // 두 수의 인덱스를 저장할 결과 배열 생성
//        int[] result = new int[2];
//
//        // 해시테이블 생성
//        // 키(Key): 찾고자 하는 값 (target - 현재 숫자)
//        // 값(Value): 현재 숫자의 인덱스
//        // 예: target=10이고 현재 숫자가 7이면, 키=3(10-7), 값=7의 인덱스
//        Hashtable<Integer, Integer> ht = new Hashtable<>();
//
//        // === 상세 실행 과정 예시 ===
//        // 예: numbers=[7,11,5,3], target=10 인 경우
//        // 1회전: 7 처리
//        //   - ht에 (3,0) 저장 // 10-7=3이 나중에 나오면 정답
//        // 2회전: 11 처리
//        //   - ht에 (-1,1) 저장 // 10-11=-1이 나중에 나오면 정답
//        // 3회전: 5 처리
//        //   - ht에 (5,2) 저장 // 10-5=5가 나중에 나오면 정답
//        // 4회전: 3 처리
//        //   - 3이 ht에 키로 있나? YES! -> 정답 [0,3]
//
//        // 배열을 처음부터 끝까지 순회
//        for (int i = 0; i < numbers.length; i++) {
//            // 현재 숫자가 해시테이블의 키로 존재하는지 확인
//            // 존재한다면 -> 이전에 나온 어떤 수와 현재 수를 더하면 target이 됨
//            if (ht.containsKey(numbers[i])) {
//                result[0] = ht.get(numbers[i]);  // 이전 숫자의 인덱스
//                result[1] = i;                   // 현재 숫자의 인덱스
//                return result;                   // 찾았으므로 결과 반환
//            }
//
//            // target에서 현재 숫자를 뺀 값을 해시테이블에 저장
//            // 이 값이 나중에 나타나면, 현재 숫자와 더해서 target이 됨
//            ht.put(target - numbers[i], i);
//
//            // 예: target=10, 현재 숫자=7일 때
//            // ht.put(3, 0) -> 나중에 3이 나오면 7과 더해서 10이 됨
//        }
//
//        // 여기까지 왔다면 target을 만드는 두 수를 찾지 못한 것
//        return null;
//    }
//
//    public static void main(String[] args) {
//        // Test code
//        // 테스트 케이스 1: 정상 케이스 (답이 있는 경우)
//        int[] nums = {7, 11, 5, 3};
//        System.out.println("테스트 1: " + Arrays.toString(solution(nums, 10)));
//        // 예상 출력: [0, 3] (7과 3을 더하면 10)
//
//        // 테스트 케이스 2: 음수가 포함된 정상 케이스
//        nums = new int[]{8, 3, -2};
//        System.out.println("테스트 2: " + Arrays.toString(solution(nums, 6)));
//        // 예상 출력: [0, 2] (8과 -2를 더하면 6)
//
//        // 테스트 케이스 3: 답이 없는 경우
//        nums = new int[]{1, 2, 3};
//        System.out.println("테스트 3: " + Arrays.toString(solution(nums, 12)));
//        // 예상 출력: null (어떤 두 수를 더해도 12가 되지 않음)
//    }
//}