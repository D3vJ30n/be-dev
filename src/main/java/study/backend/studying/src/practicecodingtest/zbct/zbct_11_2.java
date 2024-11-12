package study.backend.studying.src.practicecodingtest.zbct;

import java.util.HashSet;
import java.util.Set;

public class zbct_11_2 {
    public int solution(String[] names) {
        // duplicationNames Set을 생성하여 중복을 제거하고 고유한 이름만 저장
        Set<String> duplicationNames = new HashSet<>(); // Set 자료구조는 중복을 허용하지 않기 때문에, names 배열에 중복된 이름이 있어도 Set에 추가될 때 자동으로 중복이 제거

        // names 배열을 순회하며 중복된 이름을 제외한 고유한 이름들을 duplicationNames Set에 추가
        for (String name : names) {
            duplicationNames.add(name);
        }

        // 고유한 이름의 개수가 4개 미만이라면 네 명을 뽑을 수 없으므로 0을 반환
        if (duplicationNames.size() < 4) {
            return 0;
        } else {
            // 고유한 이름의 개수를 변수 n에 저장
            int n = duplicationNames.size();

            // 조합 공식을 사용하여 고유한 이름 n개 중 네 명을 뽑는 경우의 수를 계산
            // C(n, 4) = n * (n - 1) * (n - 2) * (n - 3) / (4 * 3 * 2 * 1) = n * (n - 1) * (n - 2) * (n - 3) / 24
            return n * (n - 1) * (n - 2) * (n - 3) / 24;
        }
    }
}