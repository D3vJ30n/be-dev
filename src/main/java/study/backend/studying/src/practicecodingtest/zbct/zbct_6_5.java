package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayList;
import java.util.Arrays;

public class zbct_6_5 {
    /**
     * 두 배열에서 공통으로 존재하는 정수를 오름차순으로 반환하는 메서드
     * Two Pointer 알고리즘 사용
     * @param arr1 첫 번째 정수 배열
     * @param arr2 두 번째 정수 배열
     * @return 공통 원소를 담은 정수 배열
     */
    public int[] solution(int[] arr1, int[] arr2) {
        // 결과를 저장할 ArrayList 생성
        // ArrayList<Object> 대신 ArrayList<Integer> 사용이 더 적절
        ArrayList<Integer> answer = new ArrayList<>();

        // 두 배열을 오름차순으로 정렬
        // 예: arr1 = {1,7,8,4} -> {1,4,7,8}
        //     arr2 = {2,4,6,8} -> {2,4,6,8}
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        // Two Pointer 방식으로 두 배열 비교
        int i = 0;  // arr1의 포인터
        int j = 0;  // arr2의 포인터

        // 두 배열 중 하나라도 끝까지 가면 종료
        while (i < arr1.length && j < arr2.length) {
            // 두 배열의 현재 값이 같으면
            if (arr1[i] == arr2[j]) {
                answer.add(arr1[i]);  // 결과 리스트에 추가, 모두 들어있는 숫자가 되니까
                i++;  // 오른쪽으로 양쪽 포인터 모두 이동
                j++;
            }
            // arr1의 현재 값이 더 작으면
            else if (arr1[i] < arr2[j]) {
                i++;  // arr1의 포인터만 이동, 두 포인터가 인덱스 0에서 출발후 arr1 포인터만 오른쪽 한칸 옆으로 가는 것, 쉽게 말해 두 개의 손가락으로 숫자를 선택하면서 arr2랑 비교한다고 생각
            }
            // arr2의 현재 값이 더 작으면
            else {
                j++;  // arr2의 포인터만 이동
            }
        }

        // ArrayList를 int[] 배열로 변환하여 반환
        return answer.stream() //스트림 생성
            .mapToInt(Integer::intValue) //Integer를 int로 변환, i -> i 가능, Integer::intValue는 메소드 레퍼런스 문법
            .toArray(); //int[] 배열로 변환
    }
}