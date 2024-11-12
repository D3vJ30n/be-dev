package study.backend.studying.src.practicecodingtest.zbct;

public class zbct1_2_2 {
    public String[] solution(String s) {
        // 단어를 특수문자를 기준으로 분리
        String[] words = s.split("[^a-zA-Z]+");
        String[] result = new String[words.length];

        // 각 단어를 뒤집어서 저장
        for(int i = 0; i < words.length; i++) {
            result[i] = new StringBuilder(words[i]).reverse().toString();
        }

        return result;
    }
}