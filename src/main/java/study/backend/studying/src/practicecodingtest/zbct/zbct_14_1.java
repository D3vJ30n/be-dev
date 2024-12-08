package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_14_1 {

    public boolean solution(String s, String t) {
        // 먼저, 두 문자열 s와 t가 애너그램이 되려면 길이가 동일해야 합니다.
        // 예를 들어 s = "abc", t = "ab"라면 길이가 달라서 절대 같은 문자 구성을 가질 수 없습니다.
        if (s.length() != t.length()) {
            return false; // 길이가 다르면 바로 false 반환
        }

        // 알파벳 소문자만 나온다고 가정하면, 'a'부터 'z'까지 26개의 문자만 고려하면 됩니다.
        // 각 문자의 출현 빈도를 저장할 int 배열을 만듭니다. 크기는 26으로 고정.
        // 인덱스 0은 'a'의 빈도, 1은 'b'의 빈도, ... 25는 'z'의 빈도를 뜻합니다.
        int[] charCount = new int[26];

        // 이제 s와 t를 한 글자씩 비교하면서 빈도 배열을 조정합니다.
        // s의 문자는 빈도를 1 증가시키고, t의 문자는 빈도를 1 감소시킵니다.
        // 결국 s와 t가 같은 문자들을 같은 개수만큼 갖고 있다면,
        // 모든 증감이 상쇄되어 최종적으로 모든 빈도가 0이 될 것입니다.
        for (int i = 0; i < s.length(); i++) {
            // s.charAt(i)는 s의 i번째 문자
            // 'a'를 빼주는 이유: 'a'를 인덱스 0에 맞추기 위해서입니다.
            // 예를 들어 'a'-'a'=0, 'b'-'a'=1 이런 식으로 문자에 대응하는 인덱스를 구할 수 있습니다.
            charCount[s.charAt(i) - 'a']++; // s의 i번째 문자에 해당하는 빈도 증가
            charCount[t.charAt(i) - 'a']--; // t의 i번째 문자에 해당하는 빈도 감소
        }

        // 모든 문자 빈도가 0인지 확인합니다.
        // 만약 어떤 문자의 빈도가 0이 아니라면, 그 문자는 s와 t에서 개수가 다르다는 뜻입니다.
        // 예를 들어 charCount[0] (즉 'a'에 해당)이 1이라면, s에 'a'가 t보다 한 개 더 많다는 의미입니다.
        // 이 경우 애너그램일 수 없으므로 false를 반환합니다.
        for (int count : charCount) {
            if (count != 0) {
                return false; // 하나라도 0이 아니면 애너그램 아님
            }
        }

        // 여기까지 왔다면 모든 문자의 빈도가 정확히 0이라는 뜻입니다.
        // 즉, s와 t는 같은 문자를 같은 개수만큼 갖고 있으므로 애너그램입니다.
        return true;
    }

    // 추가로 main 메서드를 통해 간단히 테스트해볼 수도 있습니다.
    // 예시:
    // public static void main(String[] args) {
    //     zbct_14_1 tester = new zbct_14_1();
    //     System.out.println(tester.solution("listen", "silent")); // true
    //     System.out.println(tester.solution("apple", "papel"));   // true
    //     System.out.println(tester.solution("rat", "car"));       // false
    // }
}
