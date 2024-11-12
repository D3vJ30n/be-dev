package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_hint_2_4 {
    public int solution(String str1, String str2) {
        //str1이 str1를 포함하고 있다면
        if (str1.contains(str2)) {
            //str1의 길이를 반환
            return 1;
            }
        else { //아니면

            return 0;
        }
    }
}