package servlet;

import com.google.gson.Gson;
import service.WifiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/wifi-info")
public class WifiInfoServlet extends HttpServlet {
    private final WifiService wifiService = new WifiService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("요청된 action: " + action);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if ("load".equals(action)) {
                // 와이파이 정보 로드
                int totalCount = wifiService.fetchAndSaveWifiData();

                // 응답 데이터 생성
                Map<String, Object> result = new HashMap<>();
                result.put("totalCount", totalCount);

                // JSON 응답 전송
                response.getWriter().write(gson.toJson(result));

            } else {
                // 잘못된 action 파라미터
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid action parameter");
                response.getWriter().write(gson.toJson(error));
            }
        } catch (Exception e) {
            // 서버 에러 처리
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Server error: " + e.getMessage());
            response.getWriter().write(gson.toJson(error));

            // 로그 출력
            System.err.println("Error in WifiInfoServlet: " + e.getMessage());
            e.printStackTrace();
        }
    }
}