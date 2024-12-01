package servlet;

import service.HistoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/save-location")
public class SaveLocationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try {
            // 입력값 받기
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));

            // 위도/경도 유효성 검사
            if (latitude < -90 || latitude > 90) {
                throw new IllegalArgumentException("위도는 -90에서 90 사이여야 합니다.");
            }
            if (longitude < -180 || longitude > 180) {
                throw new IllegalArgumentException("경도는 -180에서 180 사이여야 합니다.");
            }

            // 서비스 호출
            HistoryService historyService = new HistoryService();
            historyService.saveLocation(latitude, longitude);

            // 성공 시 리다이렉트
            response.sendRedirect("history.jsp");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "위도와 경도는 숫자여야 합니다.");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "저장 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}