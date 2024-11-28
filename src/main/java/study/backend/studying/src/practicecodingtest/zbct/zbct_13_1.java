package study.backend.studying.src.practicecodingtest.zbct;

import java.util.*; // 유틸리티 클래스들을 가져온다. 여기에는 ArrayList, List 등이 포함된다.

class zbct_13_1 { // Solution 클래스 선언
    // 메서드 solution: 문제를 해결하는 메인 메서드
    // 입력으로 제목(titles), 가사(lyrics), 검색어(problems)를 받고, 검색 결과를 반환한다.
    public String[][] solution(String[] titles, String[] lyrics, String[] problems) {
        List<List<String>> result = new ArrayList<>();
        // 검색 결과를 저장할 리스트의 리스트를 생성
        // 외부 리스트는 각 검색어에 대한 결과를 저장하고, 내부 리스트는 해당 검색어에 맞는 노래 제목들을 저장한다.

        for (String problem : problems) {
            // 검색어 배열인 problems를 하나씩 반복
            List<String> matchedTitles = new ArrayList<>();
            // 현재 검색어와 매칭되는 제목들을 저장할 리스트를 생성

            for (int i = 0; i < lyrics.length; i++) {
                // 가사 배열(lyrics)의 모든 항목을 순회
                if (lyrics[i].startsWith(problem)) {
                    // 현재 가사가 검색어로 시작하는지 확인
                    matchedTitles.add(titles[i]);
                    // 검색어로 시작하면 해당 제목을 matchedTitles에 추가
                }
            }

            result.add(matchedTitles);
            // 현재 검색어에 해당하는 매칭된 제목 리스트를 전체 결과 리스트에 추가
        }

        // 2차원 배열로 변환: 최종적으로 반환할 결과 형식
        String[][] answer = new String[result.size()][];
        // result의 크기에 맞는 2차원 배열을 생성. result.size()는 검색어 개수와 같다.
        for (int i = 0; i < result.size(); i++) {
            // result 리스트의 각 내부 리스트를 순회
            answer[i] = result.get(i).toArray(new String[0]);
            // 내부 리스트를 String 배열로 변환하여 answer의 해당 위치에 저장
        }

        return answer;
        // 최종적으로 검색 결과를 2차원 배열로 반환
    }
}

/* 추가 설명
ArrayList와 List

ArrayList는 크기가 가변적인 배열이라고 생각하면 된다. 크기를 미리 정할 필요가 없고, 데이터를 추가하거나 삭제할 수 있다.
    List는 인터페이스로, 다양한 리스트 형태(ArrayList, LinkedList)를 사용할 수 있는 공통의 타입이다.
toArray 메서드
리스트를 배열로 변환해주는 메서드. new String[0]은 변환된 배열의 타입을 지정하는 데 사용된다.

2차원 배열 반환
2차원 배열은 각 검색어에 대한 결과를 별도의 배열로 반환하기 위해 사용된다.
 */