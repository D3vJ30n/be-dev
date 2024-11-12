package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_10_5 {
    public String solution(int[] arr) {
        String answer = "";
        //빈 베열이나 원소가 1개인 경우는 Min Heap으로 간주
        if(arr.length == 0 || arr.length == 1) {
            answer = "YES";
            return answer;
        }
        //Min Heap인지 확인
        for(int i = 0; i < arr.length / 2; i++) {
            //왼쪽 자식 노드
            int left = i * 2 + 1;
            //오른쪽 자식 노드
            int right = i * 2 + 2;

            /*
            두 가지 표현 방식이 모두 가능

            0번 인덱스부터 시작: left = i * 2 + 1, right = i * 2 + 2
            1번 인덱스부터 시작: left = i * 2, right = i * 2 + 1

            결과적으로 같은 관계 표현

            두 방식 모두 부모-자식 간의 관계를 정확하게 표현
            Min Heap의 특성(부모가 자식보다 작거나 같음)을 올바르게 검사
             */

            //왼쪽 자식 노드가 존재하고 부모 노드보다 작은 경우
            if(left < arr.length && arr[i] > arr[left]) {
                answer = "NO";
                return answer;
            }
            //오른쪽 자식 노드가 존재하고 부모 노드보다 작은 경우
            if(right < arr.length && arr[i] > arr[right]) {
                answer = "NO";
                return answer;
            }
        }
        answer = "YES";
        return answer;
    }
}