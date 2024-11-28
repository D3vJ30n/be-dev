package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayList;
import java.util.List;

public class zbct_13_1_me {
    public String[][] solution(String[] titles, String[] lyrics, String[] problems) {
        // 결과를 저장할 2차원 리스트 생성
        List<List<String>> result = new ArrayList<>();

        // problems 배열을 순회하며 각 문제를 처리
        for (String problem : problems) {
            // 현재 문제에 대한 매칭된 제목들을 저장할 리스트 생성
            List<String> matchedTitles = new ArrayList<>();

            // lyrics 배열을 순회하며 각 가사를 처리
            for (int i = 0; i < lyrics.length; i++) {
                // 현재 가사가 문제로 시작하는지 확인
                if (lyrics[i].startsWith(problem)) {
                    // 문제로 시작하면 해당 제목을 matchedTitles에 추가
                    matchedTitles.add(titles[i]);
                }
            }

            // 현재 문제의 결과를 결과 리스트에 추가
            result.add(matchedTitles);
        }

        // 결과 리스트를 2차원 배열로 변환
        String[][] answer = new String[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i).toArray(new String[0]);
        }

        return answer; // 최종 결과 반환
    }
}
