///*
// * 제로베이스 백엔드 스쿨 30기
// * 전도명
// *
// * Mission 1 깜짝과제 1. 자바 환경 정보 html 테이블구조로 출력하기
// *
// * 자바의 시스템 속성과 파일 저장 코드는 샘플 코드를 참조해 주세요.
// * 출력결과의 파일은 웹브라우저로 확인해서 정상적으로 표시되어야 합니다.
// * html파일을 작성할 때 테이블에 라인이 표시되도록 head 태그에 style태그 추가(샘플 코드 참조)
// */
//
//package study.backend.studying.src.zbmissions;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
///**
// * 자바 시스템 속성을 HTML 테이블 형태로 생성하는 클래스
// */
//public class PropertyHtmlGenerator {
//    // HTML 문서의 기본 템플릿 (문자열 포맷팅을 위해 %s 사용)
//    private static final String HTML_TEMPLATE = "<!DOCTYPE html>\n" +
//        "<html>\n" +
//        "<head>\n" +
//        "    <meta charset=\"UTF-8\"/>\n" +
//        "    <style>\n" +
//        "        table { border-collapse: collapse; width: 100%%; }\n" +
//        "        th, td { border: solid 1px #000; }\n" +
//        "    </style>\n" +
//        "</head>\n" +
//        "<body>\n" +
//        "    <table>\n" +
//        "        <tr><th>속성 이름</th><th>값</th></tr>\n" +
//        "        %s\n" +
//        "    </table>\n" +
//        "</body>\n" +
//        "</html>";
//
//    public static void main(String[] args) {
//        // 시스템 속성값 콘솔 출력
//        System.out.println("-------- 자바 시스템 속성값 출력 시작 --------");
//        for (Object k : System.getProperties().keySet()) {
//            String key = k.toString();
//            String value = System.getProperty(key);
//            System.out.println(key + " = " + value);
//        }
//        System.out.println("-------- 자바 시스템 속성값 출력 완료 --------\n");
//
//        // HTML 파일 생성
//        try {
//            // HTML 파일 객체 생성
//            File file = new File("property.html");
//            // BufferedWriter를 사용하여 파일 쓰기 성능 향상
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//
//            // 테이블 내용 생성
//            StringBuilder tableContent = new StringBuilder();
//            // 모든 시스템 속성을 순회하며 테이블 행 생성
//            for (Object k : System.getProperties().keySet()) {
//                String key = k.toString();
//                String value = System.getProperty(key);
//                // HTML 테이블 행 추가
//                tableContent.append(String.format("<tr><td>%s</td><td>%s</td></tr>\n",
//                    key, value));
//            }
//
//            // HTML 파일 작성
//            // 테이블 내용을 HTML 템플릿에 삽입
//            writer.write(String.format(HTML_TEMPLATE, tableContent.toString()));
//            // 파일 쓰기 완료 및 리소스 해제
//            writer.close();
//
//            // 결과 출력
//            System.out.println("HTML 파일 생성 완료");
//            System.out.println("파일 경로: " + file.getAbsolutePath());
//            System.out.println("웹 브라우저에서 파일을 열어 결과를 확인하세요.");
//
//        } catch (IOException e) {
//            // 파일 생성 중 오류 발생 시 스택 트레이스 출력
//            System.err.println("파일 생성 중 오류 발생");
//            e.printStackTrace();
//        }
//    }
//}
//
///*
//실행 순서
//1. 모든 시스템 속성을 콘솔에 출력
//2. property.html 파일 생성
//3. 시스템 속성을 HTML 테이블 형식으로 변환
//4. 변환된 내용을 파일에 작성
//5. 성공/실패 메시지 출력
//
//주요 기능
//- System.getProperties(): 시스템 속성 전체 조회
//- System.getProperty(): 개별 속성값 조회
//- StringBuilder: 문자열 연결 최적화
//- BufferedWriter: 파일 쓰기 성능 향상
//
//생성되는 파일
//- 파일명: property.html
//- 내용: 시스템 속성이 담긴 HTML 테이블
//- 스타일: 테두리가 있는 기본 테이블 형식
//*/