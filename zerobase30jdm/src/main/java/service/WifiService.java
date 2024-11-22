package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.WifiApiResponse;
import model.WifiSpot;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WifiService는 와이파이 정보를 관리하는 서비스 클래스입니다.
 * API 호출과 데이터베이스 처리를 담당합니다.
 */
public class WifiService {
    // DB 연결 정보
    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    // API 관련 정보
    private static final String API_URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_KEY = "5861565a546a787839304b4e664354"; // 실제 API 키로 교체 필요

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WifiService() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MariaDB 드라이버 로드 실패", e);
        }
    }

    /**
     * API에서 와이파이 정보를 가져옵니다.
     */
    public WifiApiResponse getWifiInfoFromApi(int start, int end) throws IOException {
        String url = String.format("%s/%s/json/TbPublicWifiInfo/%d/%d", API_URL, API_KEY, start, end);

        Request request = new Request.Builder()
            .url(url)
            .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API 호출 실패: " + response);
            }

            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, WifiApiResponse.class);
        }
    }

    /**
     * 데이터베이스에 와이파이 정보를 저장합니다.
     */
    public void saveWifiSpots(List<WifiSpot> spots) {
        String sql = "INSERT INTO wifi_info (mgr_no, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, " +
            "X_SWIFI_ADRES2, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_MBY, " +
            "X_SWIFI_LAT, X_SWIFI_LNT, WORK_DTTM) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (WifiSpot spot : spots) {
                pstmt.setString(1, spot.getMgrNo());
                pstmt.setString(2, spot.getBorough());
                pstmt.setString(3, spot.getName());
                pstmt.setString(4, spot.getAddress1());
                pstmt.setString(5, spot.getAddress2());
                pstmt.setString(6, spot.getInstallType());
                pstmt.setString(7, spot.getInstallFloor());
                pstmt.setString(8, spot.getInstallAgency());
                pstmt.setDouble(9, spot.getLatitude());
                pstmt.setDouble(10, spot.getLongitude());
                pstmt.setString(11, spot.getWorkDateTime());

                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("WiFi 데이터 저장 실패", e);
        }
    }

    /**
     * 모든 와이파이 정보를 조회합니다.
     */
    public List<WifiSpot> getAllWifiSpots() {
        List<WifiSpot> wifiSpots = new ArrayList<>();
        String sql = "SELECT * FROM wifi_info";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                WifiSpot spot = new WifiSpot();
                spot.setMgrNo(rs.getString("mgr_no"));
                spot.setBorough(rs.getString("X_SWIFI_WRDOFC"));
                spot.setName(rs.getString("X_SWIFI_MAIN_NM"));
                spot.setAddress1(rs.getString("X_SWIFI_ADRES1"));
                spot.setAddress2(rs.getString("X_SWIFI_ADRES2"));
                spot.setInstallType(rs.getString("X_SWIFI_INSTL_TY"));
                spot.setInstallFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
                spot.setInstallAgency(rs.getString("X_SWIFI_INSTL_MBY"));
                spot.setLatitude(rs.getDouble("X_SWIFI_LAT"));
                spot.setLongitude(rs.getDouble("X_SWIFI_LNT"));
                spot.setWorkDateTime(rs.getString("WORK_DTTM"));

                wifiSpots.add(spot);
            }
        } catch (SQLException e) {
            throw new RuntimeException("WiFi 데이터 조회 실패", e);
        }
        return wifiSpots;
    }

    /**
     * 주어진 위치에서 가장 가까운 20개의 와이파이 정보를 조회합니다.
     */
    public List<WifiSpot> getNearbyWifiSpots(double lat, double lnt) {
        List<WifiSpot> wifiSpots = new ArrayList<>();
        String sql = "SELECT *, " +
            "(6371 * acos(cos(radians(?)) * cos(radians(X_SWIFI_LAT)) * " +
            "cos(radians(X_SWIFI_LNT) - radians(?)) + " +
            "sin(radians(?)) * sin(radians(X_SWIFI_LAT)))) AS distance " +
            "FROM wifi_info " +
            "ORDER BY distance " +
            "LIMIT 20";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.setDouble(3, lat);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WifiSpot spot = new WifiSpot();
                    spot.setMgrNo(rs.getString("mgr_no"));
                    spot.setBorough(rs.getString("X_SWIFI_WRDOFC"));
                    spot.setName(rs.getString("X_SWIFI_MAIN_NM"));
                    spot.setAddress1(rs.getString("X_SWIFI_ADRES1"));
                    spot.setAddress2(rs.getString("X_SWIFI_ADRES2"));
                    spot.setInstallType(rs.getString("X_SWIFI_INSTL_TY"));
                    spot.setInstallFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
                    spot.setInstallAgency(rs.getString("X_SWIFI_INSTL_MBY"));
                    spot.setLatitude(rs.getDouble("X_SWIFI_LAT"));
                    spot.setLongitude(rs.getDouble("X_SWIFI_LNT"));
                    spot.setWorkDateTime(rs.getString("WORK_DTTM"));

                    wifiSpots.add(spot);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("근처 WiFi 데이터 조회 실패", e);
        }
        return wifiSpots;
    }
}