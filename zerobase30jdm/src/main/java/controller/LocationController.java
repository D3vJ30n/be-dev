package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.HistoryService;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/location/save")
public class LocationController extends HttpServlet {
    private final HistoryService historyService = new HistoryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        // 요청 본문 읽기
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        System.out.println("Received JSON: " + requestBody); // 디버깅용 출력

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap;
        try {
            jsonMap = objectMapper.readValue(requestBody.toString(), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid JSON format\"}");
            return;
        }

        // 파싱된 데이터 처리
        if (jsonMap.containsKey("latitude") && jsonMap.containsKey("longitude")) {
            try {
                // 위도와 경도를 Double로 변환
                double latitude = Double.parseDouble(jsonMap.get("latitude").toString());
                double longitude = Double.parseDouble(jsonMap.get("longitude").toString());

                // 서비스 호출하여 데이터 저장
                historyService.saveLocation(latitude, longitude);

                // 성공 응답
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\": \"Location saved successfully\"}");
            } catch (NumberFormatException e) {
                // 위도나 경도가 올바르지 않은 형식일 때
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Invalid latitude or longitude format\"}");
            }
        } else {
            // 위도 또는 경도가 누락된 경우
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Missing latitude or longitude\"}");
        }
    }
}
