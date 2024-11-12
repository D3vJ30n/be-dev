package study.backend.studying.src.practicecodingtest.zbct;

import java.util.Arrays;

public class zbct_3_5 {
    public String solution(String[] BJ, String[] one, String[] two) {
        // 배열을 List로 변환하여 contains 메서드 사용 가능하게 함
        // 배열보다 List가 검색이 빠름
        var oneList = Arrays.asList(one); // 한 그릇 먹은 BJ 리스트
        var twoList = Arrays.asList(two); // 두 그릇 먹은 BJ 리스트

        // Stream API를 사용해서 우승자 찾기
        String winner = Arrays.stream(BJ) // BJ 배열을 스트림으로 변환
            .filter(bj -> !oneList.contains(bj) && !twoList.contains(bj)) // one, two 리스트에 없는 BJ 필터링, contains는 "포함하다" 라는 의미
            .findFirst() // 첫 번째 요소 가져오기 (우승자는 한 명), 보통 orElse와 같이 사용
            .orElse("전원 실패"); // 없으면 문자열 반환

        // 총 상금 계산
        // 한 그릇 먹은 사람 수 + 두 그릇 먹은 사람 수 * 2 + 우승자 3그릇
        int totalBowls = one.length + (two.length * 2) + 3;
        // 총 상금 = 그릇 수 * 150만원
        int prize = totalBowls * 150;

        // String.format으로 결과 문자열 생성
        // %d: 정수, %s: 문자열 형식으로 포맷팅
        return String.format("%d만원(%s)", prize, winner);
    }
}