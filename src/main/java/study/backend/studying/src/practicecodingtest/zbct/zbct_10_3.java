package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayDeque;
import java.util.Deque;

/*
[문제 해석]
1. 라이프 수치 순서대로 카드를 덱에서 제거하는 게임
2. cards 배열에는 각 카드의 라이프가 담겨있음 
3. 두 장의 카드를 뽑아서 비교하여 덱에서 카드 제거
4. x == y이면 둘 다 제거
5. x > y이면 y만 제거하고 x는 다시 덱에 넣음
6. y > x이면 x만 제거하고 y는 다시 덱에 넣음
7. 모든 카드가 제거될 때까지 반복
8. 마지막에 남은 카드 개수를 반환

[의사코드]
1. 결과를 저장할 변수 선언
2. 게임 진행 루프 (카드가 1장 이상 남아있는 동안)
  1) 카드 덱에서 첫 번째 카드 뽑기
  2) 남은 카드가 없으면 현재 덱의 카드 수 반환
  3) 카드 덱에서 두 번째 카드 뽑기
  4) 두 카드의 라이프 수치 비교
     - x == y : 두 카드 모두 제거
     - x > y : y카드 제거하고 x카드는 덱에 다시 넣기
     - y > x : x카드 제거하고 y카드는 덱에 다시 넣기
3. 남은 카드 수 반환

[개선 사항]
1. ArrayList → ArrayDeque 변경
  - ArrayDeque: 양쪽에서 요소를 추가/제거할 수 있는 더 효율적인 자료구조
  - 첫 요소 제거 시 O(1) 시간 복잡도 (ArrayList는 O(n))
  - null 요소를 허용하지 않아 더 안전
  
2. 메서드 선택
  - remove(0) → pollFirst(): 첫 번째 요소 제거 및 반환
  - add() → offerLast(): 마지막에 요소 추가
  - size() → isEmpty(): 빈 상태 확인에 더 적합

3. 초기 용량 지정으로 메모리 효율성 개선
*/

class zbct_10_3 {
    public int solution(int[] cards) {
        // 1. ArrayDeque 초기화 (초기 용량을 cards 길이로 지정)
        Deque<Integer> deck = new ArrayDeque<>(cards.length);

        // 2. 배열의 요소를 덱에 추가
        for(int card : cards) {
            deck.offerLast(card);
        }

        // 3. 게임 진행 (카드가 2장 이상일 때)
        while(deck.size() > 1) {
            // 3-1. 첫 번째 카드 뽑기
            int x = deck.pollFirst();

            // 3-2. 덱이 비었는지 확인
            if(deck.isEmpty()) {
                deck.offerLast(x);
                break;
            }

            // 3-3. 두 번째 카드 뽑기
            int y = deck.pollFirst();

            // 3-4. 카드 비교 및 처리
            if(x == y) {
                // 같으면 둘 다 제거 (이미 제거된 상태)
                continue;
            } else if(x > y) {
                // x가 크면 x만 덱에 다시 넣기
                deck.offerLast(x);
            } else {
                // y가 크면 y만 덱에 다시 넣기
                deck.offerLast(y);
            }
        }

        // 4. 남은 카드 수 반환
        return deck.size();
    }
}

/*
[성능 개선 포인트]
1. 시간 복잡도
  - ArrayList remove(0): O(n) → ArrayDeque pollFirst(): O(1)
  - 전체 시간 복잡도: O(n^2) → O(n)

2. 공간 복잡도
  - 초기 용량 지정으로 불필요한 배열 재할당 방지
  - ArrayDeque가 더 메모리 효율적

3. 안전성
  - null 요소 불가능
  - 더 명확한 메서드명으로 코드 가독성 향상
*/