package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Arrays;

public class zbct_hint_2_2_2 {
    /**
     * 정수 배열의 원소들을 주어진 방향으로 한 칸씩 회전하는 메서드
     * System.arraycopy 사용으로 성능 최적화
     *
     * @param numbers   회전할 정수 배열
     * @param direction 회전 방향 ("right" 또는 "left")
     * @return 회전된 새로운 배열
     */
    public int[] solution(int[] numbers, String direction) {
        // 입력값 유효성 검사
        if (numbers == null || numbers.length == 0) {
            return new int[]{};
        }

        // 배열 길이를 변수에 저장 (재사용)
        int length = numbers.length;
        // 원본 배열 보존을 위한 새로운 배열 생성
        int[] result = new int[length];

        // 방향에 따른 회전 처리
        switch (direction) {
            case "right":
                // 마지막 원소를 첫 위치에 배치
                result[0] = numbers[length - 1];
                // 나머지 원소들을 한 칸씩 오른쪽으로 이동 (한 번의 복사 작업)
                System.arraycopy(numbers, 0, result, 1, length - 1);
                break;

            case "left":
                // 첫 번째 원소를 마지막 위치에 배치
                result[length - 1] = numbers[0];
                // 나머지 원소들을 한 칸씩 왼쪽으로 이동 (한 번의 복사 작업)
                System.arraycopy(numbers, 1, result, 0, length - 1);
                break;

            default:
                // 잘못된 direction 값이 들어온 경우 원본 배열의 복사본 반환
                return Arrays.copyOf(numbers, length);
        }

        return result;
    }
}
/*

그림과 함께 단계별로 설명해드리겠습니다.

**오른쪽 회전 ("right") 예시: [1, 2, 3, 4] → [4, 1, 2, 3]**
```
1. 초기 상태:
numbers = [1, 2, 3, 4]
result  = [_, _, _, _]  (_ 는 빈 공간)

2. result[0] = numbers[length-1] 실행:
numbers = [1, 2, 3, 4]
result  = [4, _, _, _]  (마지막 원소 4를 맨 앞에 배치)

3. System.arraycopy(numbers, 0, result, 1, length-1) 실행:
   - numbers의 0번째부터
   - result의 1번째에
   - length-1(3)개 만큼 복사
numbers = [1, 2, 3, 4]
result  = [4, 1, 2, 3]  (1,2,3을 한번에 복사)
```

**왼쪽 회전 ("left") 예시: [1, 2, 3, 4] → [2, 3, 4, 1]**
```
1. 초기 상태:
numbers = [1, 2, 3, 4]
result  = [_, _, _, _]  (_ 는 빈 공간)

2. result[length-1] = numbers[0] 실행:
numbers = [1, 2, 3, 4]
result  = [_, _, _, 1]  (첫 번째 원소 1을 맨 뒤에 배치)

3. System.arraycopy(numbers, 1, result, 0, length-1) 실행:
   - numbers의 1번째부터
   - result의 0번째에
   - length-1(3)개 만큼 복사
numbers = [1, 2, 3, 4]
result  = [2, 3, 4, 1]  (2,3,4를 한번에 복사)
```

**System.arraycopy 설명:**
```java
System.arraycopy(원본배열, 원본시작위치, 목적배열, 목적시작위치, 복사할개수);

예시:
System.arraycopy(numbers, 0, result, 1, 3);
// numbers의 0번째부터 시작해서
// result의 1번째 위치에
// 3개의 원소를 복사
```

이렇게 하면 반복문을 사용하지 않고도 한 번의 명령으로 여러 원소를 효율적으로 이동시킬 수 있습니다.

예시:
1. 오른쪽 회전 [1,2,3,4] -> [4,1,2,3]
  - result[0] = 4 (마지막 원소)
  - System.arraycopy([1,2,3,4], 0, result, 1, 3)
  - 결과: [4,1,2,3]

2. 왼쪽 회전 [1,2,3,4] -> [2,3,4,1]
  - result[3] = 1 (첫 번째 원소)
  - System.arraycopy([1,2,3,4], 1, result, 0, 3)
  - 결과: [2,3,4,1]

System.arraycopy 장점:
1. 네이티브 메서드로 최적화됨
2. 수동 반복문보다 빠름
3. 안전한 배열 복사
*/
