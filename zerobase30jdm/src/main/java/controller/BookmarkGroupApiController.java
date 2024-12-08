package controller;

import com.fasterxml.jackson.databind.ObjectMapper; // JSON 처리를 위한 Jackson 라이브러리
import model.BookmarkGroup; // 북마크 그룹 데이터를 표현하는 모델 클래스
import service.BookmarkGroupService; // 북마크 그룹 관련 비즈니스 로직을 처리하는 서비스 클래스

import javax.servlet.ServletException; // 서블릿 예외 처리를 위한 클래스
import javax.servlet.annotation.WebServlet; // 서블릿 URL 매핑을 위한 어노테이션
import javax.servlet.http.HttpServlet; // HttpServlet 클래스를 상속받아 서블릿 구현
import javax.servlet.http.HttpServletRequest; // HTTP 요청 객체
import javax.servlet.http.HttpServletResponse; // HTTP 응답 객체
import java.io.IOException; // 입출력 처리 중 발생하는 예외를 처리하기 위한 클래스
import java.util.List; // List 컬렉션을 사용하기 위한 클래스

/**
 * 북마크 그룹 목록을 반환하는 API 컨트롤러 서블릿.
 * "/bookmark-group/list" URL에 대한 GET 요청을 처리한다.
 */
@WebServlet("/bookmark-group/list")
public class BookmarkGroupApiController extends HttpServlet {

    // BookmarkGroupService 인스턴스 초기화
    // 의존성 주입 대신 객체를 직접 생성하여 사용
    private final BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();

    /**
     * HTTP GET 요청 처리 메서드.
     * 북마크 그룹 목록을 조회하여 JSON 형식으로 응답한다.
     *
     * @param req  HTTP 요청 객체
     * @param resp HTTP 응답 객체
     * @throws ServletException 서블릿에서 발생하는 예외 처리
     * @throws IOException      입출력 처리 예외
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS 설정: 모든 도메인에서의 요청 허용
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // 응답 인코딩 설정 (UTF-8)
        resp.setCharacterEncoding("UTF-8");
        // 응답 Content-Type을 JSON으로 설정
        resp.setContentType("application/json");

        try {
            // 서비스 계층에서 북마크 그룹 목록을 조회
            List<BookmarkGroup> groups = bookmarkGroupService.getBookmarkGroupList();

            // 조회된 데이터를 JSON 형식으로 변환하여 응답
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(groups));
        } catch (Exception e) {
            // 에러가 발생하면 HTTP 상태 코드를 500으로 설정
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // 클라이언트에 에러 메시지 응답
            resp.getWriter().write("{\"error\": \"Failed to fetch bookmark groups\"}");
        }
    }
}
