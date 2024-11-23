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

public class WifiService {
    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    private static final String API_URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_KEY = "5861565a546a787839304b4e664354";

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

    public WifiApiResponse getWifiInfoFromApi(int start, int end) throws IOException {
        String url = String.format("%s/%s/json/TbPublicWifiInfo/%d/%d", API_URL, API_KEY, start, end);

        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API 호출 실패: " + response);
            }

            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, WifiApiResponse.class);
        }
    }

    public void saveWifiSpots(List<WifiSpot> spots) {
        if (spots == null || spots.isEmpty()) {
            System.err.println("저장할 데이터가 없습니다.");
            return;
        }

        String sql = "INSERT INTO wifi_info (x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, " +
            "x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor, x_swifi_instl_ty, " +
            "x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, x_swifi_cnstc_year, " +
            "x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (WifiSpot spot : spots) {
                if (spot == null) {
                    System.err.println("Null 데이터가 감지되었습니다. 무시합니다.");
                    continue;
                }

                pstmt.setString(1, spot.getMgrNo());
                pstmt.setString(2, spot.getBorough());
                pstmt.setString(3, spot.getName());
                pstmt.setString(4, spot.getAddress1());
                pstmt.setString(5, spot.getAddress2());
                pstmt.setString(6, spot.getInstallFloor());
                pstmt.setString(7, spot.getInstallType());
                pstmt.setString(8, spot.getInstallAgency());
                pstmt.setString(9, spot.getServiceType());
                pstmt.setString(10, spot.getNetworkType());
                pstmt.setString(11, spot.getInstallYear());
                pstmt.setString(12, spot.getIndoorOutdoor());
                pstmt.setString(13, spot.getRemarks());
                pstmt.setDouble(14, spot.getLatitude());
                pstmt.setDouble(15, spot.getLongitude());
                pstmt.setString(16, spot.getWorkDateTime());

                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("WiFi 데이터 저장 실패", e);
        }
    }

    public List<WifiSpot> getAllWifiSpots() {
        List<WifiSpot> wifiSpots = new ArrayList<>();
        String sql = "SELECT * FROM wifi_info";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                WifiSpot spot = new WifiSpot();
                spot.setMgrNo(rs.getString("x_swifi_mgr_no"));
                spot.setBorough(rs.getString("x_swifi_wrdofc"));
                spot.setName(rs.getString("x_swifi_main_nm"));
                spot.setAddress1(rs.getString("x_swifi_adres1"));
                spot.setAddress2(rs.getString("x_swifi_adres2"));
                spot.setInstallFloor(rs.getString("x_swifi_instl_floor"));
                spot.setInstallType(rs.getString("x_swifi_instl_ty"));
                spot.setInstallAgency(rs.getString("x_swifi_instl_mby"));
                spot.setServiceType(rs.getString("x_swifi_svc_se"));
                spot.setNetworkType(rs.getString("x_swifi_cmcwr"));
                spot.setInstallYear(rs.getString("x_swifi_cnstc_year"));
                spot.setIndoorOutdoor(rs.getString("x_swifi_inout_door"));
                spot.setRemarks(rs.getString("x_swifi_remars3"));
                spot.setLatitude(rs.getDouble("lat"));
                spot.setLongitude(rs.getDouble("lnt"));
                spot.setWorkDateTime(rs.getString("work_dttm"));

                wifiSpots.add(spot);
            }
        } catch (SQLException e) {
            System.err.println("데이터 조회 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("WiFi 데이터 조회 실패", e);
        }
        return wifiSpots;
    }

    public int fetchAndSaveWifiData() {
        int totalSaved = 0;
        int batchSize = 1000;
        int start = 1;

        try {
            WifiApiResponse initialResponse = getWifiInfoFromApi(1, 1);
            int totalCount = initialResponse.getWifiInfo().getTotalCount();
            System.out.println("전체 데이터 개수: " + totalCount);

            while (start <= totalCount) {
                int end = Math.min(start + batchSize - 1, totalCount);
                System.out.printf("API 호출: %d ~ %d%n", start, end);

                WifiApiResponse response = getWifiInfoFromApi(start, end);
                List<WifiSpot> spots = response.getWifiInfo().getSpots();

                saveWifiSpots(spots);
                totalSaved += spots.size();

                start += batchSize;
            }
        } catch (IOException e) {
            System.err.println("API 데이터 가져오기 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("API 데이터 가져오기 중 오류 발생", e);
        }

        System.out.printf("총 저장된 데이터 개수: %d%n", totalSaved);
        return totalSaved;
    }
}
