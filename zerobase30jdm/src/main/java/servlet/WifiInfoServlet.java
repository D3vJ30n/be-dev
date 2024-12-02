package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.WifiInfo;
import model.WifiSpot;
import service.WifiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "wifiInfoServlet", urlPatterns = "/wifi-info")
public class WifiInfoServlet extends HttpServlet {
    private final WifiService wifiService = new WifiService();
    private final Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // CORS 헤더 추가
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String action = request.getParameter("action");

            if ("nearby".equals(action)) {
                handleNearbyRequest(request, response);
            } else if ("saveHistory".equals(action)) {
                handleSaveHistoryRequest(request, response);
            } else if ("detail".equals(action)) {    // <- 여기에 새로운 케이스 추가
                handleDetailRequest(request, response);
            } else {
                out.write("{\"error\": \"유효하지 않은 action 파라미터입니다.\"}");
            }
        } catch (Exception e) {
            out.write("{\"error\": \"서버 오류가 발생했습니다.\"}");
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

    private void handleNearbyRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        PrintWriter out = response.getWriter();
        String latStr = request.getParameter("lat");
        String lntStr = request.getParameter("lnt");
        String radiusParam = request.getParameter("radius");

        System.out.println("반경값(radius): " + radiusParam);

        if (latStr == null || latStr.trim().isEmpty() ||
            lntStr == null || lntStr.trim().isEmpty() ||
            radiusParam == null || radiusParam.trim().isEmpty()) {
            out.write("{\"error\": \"위도, 경도 및 반경값을 모두 입력해주세요.\"}");
            return;
        }

        try {
            double lat = Double.parseDouble(latStr.trim());
            double lnt = Double.parseDouble(lntStr.trim());
            double radius = Double.parseDouble(radiusParam.trim());

            if (radius <= 0) {
                radius = 30.0;
            }

            if (lat < -90 || lat > 90 || lnt < -180 || lnt > 180) {
                out.write("{\"error\": \"유효하지 않은 위도, 경도 값입니다.\"}");
                return;
            }

            List<WifiInfo> nearbyWifiList = wifiService.getNearestWifi(lat, lnt, radius);
            out.write(gson.toJson(nearbyWifiList));

            // Pretty Print JSON 추가
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(nearbyWifiList);
            System.out.println("Pretty JSON 출력: \n" + prettyJson); // 서버 콘솔에 출력

        } catch (NumberFormatException e) {
            out.write("{\"error\": \"위도, 경도 및 반경값은 숫자여야 합니다.\"}");
        }
    }

    private void handleSaveHistoryRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        PrintWriter out = response.getWriter();
        String latStr = request.getParameter("lat");
        String lntStr = request.getParameter("lnt");

        if (latStr == null || latStr.trim().isEmpty() ||
            lntStr == null || lntStr.trim().isEmpty()) {
            out.write("{\"error\": \"위도와 경도를 모두 입력해주세요.\"}");
            return;
        }

        try {
            double lat = Double.parseDouble(latStr.trim());
            double lnt = Double.parseDouble(lntStr.trim());

            // TODO: 위치 히스토리 저장 로직 구현
            // wifiService.saveLocationHistory(lat, lnt);

            out.write("{\"status\": \"success\", \"message\": \"위치 히스토리가 저장되었습니다.\"}");
        } catch (NumberFormatException e) {
            out.write("{\"error\": \"위도와 경도는 숫자여야 합니다.\"}");
        }
    }

    private void handleDetailRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        PrintWriter out = response.getWriter();
        String mgrNo = request.getParameter("mgrNo");

        if (mgrNo == null || mgrNo.trim().isEmpty()) {
            out.write("{\"error\": \"관리번호가 필요합니다.\"}");
            return;
        }

        try {
            WifiSpot wifiSpot = wifiService.getWifiSpot(mgrNo);

            if (wifiSpot == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"error\": \"해당하는 와이파이 정보를 찾을 수 없습니다.\"}");
                return;
            }

            out.write(gson.toJson(wifiSpot));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}