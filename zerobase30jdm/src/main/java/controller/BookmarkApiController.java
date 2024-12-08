package controller;

import com.fasterxml.jackson.databind.ObjectMapper; // JSON 객체 변환을 위한 라이브러리 임포트
import model.Bookmark; // Bookmark 모델 클래스 임포트
import service.BookmarkService; // Bookmark 관련 서비스 클래스 임포트

import javax.servlet.ServletException; // 서블릿 예외 처리를 위한 클래스
import javax.servlet.annotation.WebServlet; // 서블릿 매핑을 위한 어노테이션
import javax.servlet.http.HttpServlet; // 기본 HttpServlet 클래스
import javax.servlet.http.HttpServletRequest; // HTTP 요청 객체
import javax.servlet.http.HttpServletResponse; // HTTP 응답 객체
import java.io.IOException; // 입출력 예외 처리를 위한 클래스

// 서블릿의 URL 매핑 지정
@WebServlet("/bookmark/add")
public class BookmarkApiController extends HttpServlet {
    // Bookmark 관련 비즈니스 로직 처리를 위한 서비스 객체 생성
    private final BookmarkService bookmarkService = new BookmarkService();

    // HTTP POST 요청 처리 메서드
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS 설정: 모든 도메인에서 요청 허용
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // CORS 설정: 허용되는 HTTP 메서드 지정
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        // CORS 설정: 요청 헤더에 Content-Type 허용
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        // 응답 문자 인코딩 설정
        resp.setCharacterEncoding("UTF-8");
        // 응답 Content-Type 설정
        resp.setContentType("application/json");

        try {
            // JSON 요청 데이터를 Bookmark 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Bookmark bookmark = objectMapper.readValue(req.getReader(), Bookmark.class);

            // 변환된 객체를 서비스 계층으로 전달하여 데이터 삽입 처리
            bookmarkService.insertBookmark(bookmark);

            // 성공 메시지를 JSON 형식으로 응답
            resp.getWriter().write("{\"message\": \"Bookmark added successfully\"}");
        } catch (Exception e) {
            // 예외 발생 시 HTTP 상태 코드 500 설정
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // 에러 메시지를 JSON 형식으로 응답
            resp.getWriter().write("{\"error\": \"Failed to add bookmark\"}");
        }
    }
}
