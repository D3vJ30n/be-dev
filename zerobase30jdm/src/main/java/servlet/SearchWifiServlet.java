package servlet;

import model.WifiInfo;
import service.WifiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/search-wifi")
public class SearchWifiServlet extends HttpServlet {
    private final WifiService wifiService = new WifiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();
        try {
            // 클라이언트로부터 위도와 경도 값 받기
            double latitude = Double.parseDouble(request.getParameter("lat"));
            double longitude = Double.parseDouble(request.getParameter("lnt"));

            // 유효성 검사
            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"위도는 -90~90, 경도는 -180~180 사이여야 합니다.\"}");
                return;
            }

            // 근처 와이파이 검색
            List<WifiInfo> nearestWifi = wifiService.getNearestWifi(latitude, longitude);

            // JSON으로 변환 후 응답
            String jsonResponse = toJson(nearestWifi);
            response.setStatus(HttpServletResponse.SC_OK);
            out.write(jsonResponse);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\":\"위도와 경도는 숫자여야 합니다.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"서버 내부 오류가 발생했습니다.\"}");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    // 리스트를 JSON 형식으로 변환하는 메서드
    private String toJson(List<WifiInfo> wifiList) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < wifiList.size(); i++) {
            WifiInfo wifi = wifiList.get(i);
            json.append("{");
            json.append("\"mgrNo\":\"").append(wifi.getMgrNo()).append("\",");
            json.append("\"borough\":\"").append(wifi.getBorough()).append("\",");
            json.append("\"name\":\"").append(wifi.getName()).append("\",");
            json.append("\"latitude\":").append(wifi.getLatitude()).append(",");
            json.append("\"longitude\":").append(wifi.getLongitude());
            json.append("}");
            if (i < wifiList.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }
}
