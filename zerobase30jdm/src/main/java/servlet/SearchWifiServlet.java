package servlet;

import com.google.gson.Gson;
import model.WifiInfo;
import service.WifiService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SearchWifiServlet extends HttpServlet {
    private final WifiService wifiService = new WifiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();
        try {
            // 위도와 경도, 반경값 요청 매개변수에서 가져오기
            double latitude = Double.parseDouble(request.getParameter("lat"));
            double longitude = Double.parseDouble(request.getParameter("lnt"));
            // 수정된 부분
            String radiusParam = request.getParameter("radius");
            double radius = 30.0; // 기본값 설정

            if (radiusParam != null && !radiusParam.trim().isEmpty()) {
                radius = Double.parseDouble(radiusParam); // 파라미터로 전달받은 반경값 설정
            }

            // 클라이언트에서 서버로 전달된 latitude, longitude, radius 값이 정상적으로 들어오는지 확인
            System.out.println("Received latitude: " + latitude);
            System.out.println("Received longitude: " + longitude);
            System.out.println("Received radius: " + radius);

            // 위도, 경도, 반경값 유효성 검사
            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180 || radius <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"유효하지 않은 위도, 경도 또는 반경값입니다.\"}");
                return;
            }

            List<WifiInfo> nearestWifi = wifiService.getNearestWifi(latitude, longitude, radius);
            String jsonResponse = new Gson().toJson(nearestWifi);
            response.setStatus(HttpServletResponse.SC_OK);
            out.write(jsonResponse);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\":\"위도, 경도 및 반경값은 숫자여야 합니다.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"서버 내부 오류가 발생했습니다.\"}");
            e.printStackTrace();
        } finally {
            out.close();
        }

    }

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
