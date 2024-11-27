package servlet;

import com.google.gson.Gson;
import model.WifiInfo;
import model.WifiSpot;
import service.WifiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/wifi-info")
public class WifiInfoServlet extends HttpServlet {

    private final WifiService wifiService = new WifiService(); // 와이파이 서비스 객체

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("load".equals(action)) {
            // 와이파이 정보 가져오기
            int totalCount = wifiService.fetchAndSaveWifiData(); // Open API 데이터 가져오기 및 DB 저장
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"totalCount\": " + totalCount + "}");
        } else if ("search".equals(action)) {
            // 주변 와이파이 검색
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lng = Double.parseDouble(request.getParameter("lng"));
            List<WifiSpot> nearbyWifi = wifiService.searchNearbyWifi(lat, lng); // 위치 기반 검색
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(nearbyWifi));
        } else {
            // 잘못된 요청 처리
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid action\", \"status\": 400}");
        }
    }
}
