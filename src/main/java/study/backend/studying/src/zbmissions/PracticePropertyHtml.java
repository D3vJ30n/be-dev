//package study.backend.studying.src.zbmissions;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.FileOutputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.Properties;
//
///**
// * 자바 시스템 속성을 HTML 테이블 형태로 생성하는 클래스
// * html 파일을 생성하여 시스템 속성을 테이블 형태로 표시
// */
//public class PracticePropertyHtml {
//    public static void main(String[] args) {
//        // Java 8에서는 FileWriter에 직접 charset을 지정할 수 없으므로 OutputStreamWriter 사용
//        try (BufferedWriter writer = new BufferedWriter(
//            new OutputStreamWriter(
//                new FileOutputStream("property2.html"), StandardCharsets.UTF_8))) {
//
//            // 1. DOCTYPE 선언 및 HTML 기본 구조 작성
//            writer.write("<!DOCTYPE html>\n");  // HTML5 문서 선언
//            writer.write("<html>\n");
//            writer.write("<head>\n");
//            writer.write("    <meta charset=\"UTF-8\">\n");  // 문서 인코딩 설정
//            writer.write("    <title>시스템 환경정보</title>\n");  // 브라우저 탭에 표시될 제목
//
//            // 2. CSS 스타일 정의
//            writer.write("    <style>\n");
//            // 테이블 기본 스타일 설정
//            writer.write("        table {\n");
//            writer.write("            border-collapse: collapse;\n");  // 테두리 겹침 제거
//            writer.write("            width: 100%;\n");               // 테이블 전체 너비
//            writer.write("            margin-bottom: 20px;\n");       // 아래쪽 여백
//            writer.write("        }\n");
//            // 테이블 셀 스타일 설정
//            writer.write("        th, td {\n");
//            writer.write("            border: solid 1px #000;\n");    // 테두리 스타일
//            writer.write("            padding: 8px;\n");             // 셀 내부 여백
//            writer.write("            text-align: left;\n");         // 텍스트 왼쪽 정렬
//            writer.write("        }\n");
//            // 테이블 헤더 스타일 설정
//            writer.write("        th {\n");
//            writer.write("            background-color: #f2f2f2;\n"); // 헤더 배경색
//            writer.write("        }\n");
//            writer.write("    </style>\n");
//            writer.write("</head>\n");
//
//            // 3. 본문 시작
//            writer.write("<body>\n");
//            writer.write("    <h1>시스템 속성 정보</h1>\n");  // 페이지 제목
//
//            // 4. 테이블 구조 작성
//            writer.write("    <table>\n");
//            writer.write("        <tr>\n");
//            writer.write("            <th>키</th>\n");     // 속성 이름 열
//            writer.write("            <th>값</th>\n");     // 속성 값 열
//            writer.write("        </tr>\n");
//
//            // 5. 시스템 속성 처리 및 테이블 내용 생성
//            Properties properties = System.getProperties();  // 시스템 속성 전체 가져오기
//            // 각 시스템 속성을 순회하며 테이블 행 생성
//            for (String key : properties.stringPropertyNames()) {
//                String value = properties.getProperty(key);
//                // HTML 형식의 테이블 행 생성 (특수문자 이스케이프 처리 포함)
//                writer.write(String.format("        <tr>\n            <td>%s</td>\n            <td>%s</td>\n        </tr>\n",
//                    escapeHtml(key),     // 키값의 HTML 이스케이프 처리
//                    escapeHtml(value))); // 속성값의 HTML 이스케이프 처리
//            }
//
//            // 6. HTML 문서 종료
//            writer.write("    </table>\n");
//            writer.write("</body>\n");
//            writer.write("</html>\n");
//
//        } catch (IOException e) {
//            // 파일 생성 중 오류 발생 시 에러 메시지 출력
//            System.err.println("파일 생성 중 오류 발생: " + e.getMessage());
//            e.printStackTrace();  // 상세 에러 정보 출력
//        }
//    }
//
//    /**
//     * HTML 특수문자를 이스케이프 처리하는 메소드
//     * XSS(Cross-Site Scripting) 공격 방지와 HTML 문법 오류 방지를 위해 필요
//     *
//     * @param text 이스케이프 처리할 문자열
//     * @return 이스케이프 처리된 문자열
//     */
//    private static String escapeHtml(String text) {
//        if (text == null) return "";  // null 입력 처리
//        return text.replace("&", "&amp;")    // & 문자 치환
//            .replace("<", "&lt;")     // < 문자 치환
//            .replace(">", "&gt;")     // > 문자 치환
//            .replace("\"", "&quot;")  // 큰따옴표 치환
//            .replace("'", "&#39;");   // 작은따옴표 치환
//    }
//}