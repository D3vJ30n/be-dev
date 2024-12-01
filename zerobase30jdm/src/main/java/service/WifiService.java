package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.HistoryDao;
import dao.WifiInfoDao;
import model.History;
import model.WifiApiResponse;
import model.WifiInfo;
import model.WifiSpot;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.DBUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiService {
    private static final double DEFAULT_RADIUS = 30.0; // 기본 반경값 상수 추가
    private final WifiInfoDao wifiInfoDao = new WifiInfoDao();
    private final HistoryDao historyDao = new HistoryDao();

    // 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1?useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Jdm4568396*";

    // API 정보
    private static final String API_URL = "http://openapi.seoul.go.kr:8088/4d615350666a7878383377454f7066/json/TbPublicWifiInfo/";
    private static final String API_KEY = "4d615350666a7878383377454f7066";

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
     * ResultSet에서 WifiSpot 객체를 생성하는 헬퍼 메서드
     */
    private WifiSpot createWifiSpotFromResultSet(ResultSet rs) throws SQLException {
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
        return spot;
    }

    /**
     * 근처 Wi-Fi 검색 메서드 (반경값 필수)
     */
    public List<WifiInfo> getNearestWifi(double latitude, double longitude, double radius) {
        return wifiInfoDao.findNearbyWifi(latitude, longitude, radius);
    }

    /**
     * 기본 반경을 사용하는 getNearestWifi (2개 파라미터)
     */
    public List<WifiInfo> getNearestWifi(double latitude, double longitude) {
        return wifiInfoDao.findNearbyWifi(latitude, longitude, DEFAULT_RADIUS);
    }

    /**
     * API에서 와이파이 정보를 가져오는 메서드
     */
    public WifiApiResponse getWifiInfoFromApi(int start, int end) throws IOException {
        String url = API_URL + start + "/" + end;
        System.out.println("API 호출 URL: " + url);

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
     * API에서 데이터를 가져와서 DB에 저장
     */
    public int fetchAndSaveWifiData() {
        int totalSaved = 0;
        int batchSize = 1000;
        int start = 1;

        try (Connection conn = getConnection()) {
            System.out.println("DB 연결 성공");
        } catch (SQLException e) {
            System.err.println("DB 연결 실패: " + e.getMessage());
            return totalSaved;
        }

        try {
            clearWifiData();

            WifiApiResponse initialResponse = getWifiInfoFromApi(1, 1);
            int totalCount = initialResponse.getWifiInfo().getTotalCount();
            System.out.println("전체 데이터 개수: " + totalCount);

            while (start <= totalCount) {
                int end = Math.min(start + batchSize - 1, totalCount);
                System.out.printf("API 호출: %d ~ %d%n", start, end);

                WifiApiResponse response = getWifiInfoFromApi(start, end);

                if (response == null) {
                    throw new RuntimeException("API 응답이 null입니다.");
                }

                if (response.getWifiInfo() == null) {
                    throw new RuntimeException("wifiInfo가 null입니다.");
                }

                List<WifiSpot> spots = response.getWifiInfo().getSpots();
                if (spots == null || spots.isEmpty()) {
                    System.out.println("Spots 리스트가 null이거나 비어 있습니다.");
                } else {
                    saveWifiSpots(spots);
                    totalSaved += spots.size();
                }

                start += batchSize;
            }
        } catch (IOException e) {
            System.err.println("API 데이터 가져오기 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("API 데이터 가져오기 중 오류 발생", e);
        }

        System.out.printf("총 저장된 데이터 개수: %d%n", totalSaved);
        return totalSaved;
    }

    /**
     * 와이파이 정보를 DB에 저장
     */
    private void saveWifiSpots(List<WifiSpot> spots) {
        if (spots == null || spots.isEmpty()) {
            System.err.println("저장할 데이터가 없습니다.");
            return;
        }

        String sql = "INSERT INTO wifi_info (x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, " +
            "x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor, x_swifi_instl_ty, " +
            "x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, x_swifi_cnstc_year, " +
            "x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE " +
            "x_swifi_wrdofc = VALUES(x_swifi_wrdofc), " +
            "x_swifi_main_nm = VALUES(x_swifi_main_nm), " +
            "x_swifi_adres1 = VALUES(x_swifi_adres1), " +
            "x_swifi_adres2 = VALUES(x_swifi_adres2), " +
            "x_swifi_instl_floor = VALUES(x_swifi_instl_floor), " +
            "x_swifi_instl_ty = VALUES(x_swifi_instl_ty), " +
            "x_swifi_instl_mby = VALUES(x_swifi_instl_mby), " +
            "x_swifi_svc_se = VALUES(x_swifi_svc_se), " +
            "x_swifi_cmcwr = VALUES(x_swifi_cmcwr), " +
            "x_swifi_cnstc_year = VALUES(x_swifi_cnstc_year), " +
            "x_swifi_inout_door = VALUES(x_swifi_inout_door), " +
            "x_swifi_remars3 = VALUES(x_swifi_remars3), " +
            "lat = VALUES(lat), " +
            "lnt = VALUES(lnt), " +
            "work_dttm = VALUES(work_dttm)";

        try (Connection conn = getConnection();
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

    /**
     * 모든 와이파이 정보 조회
     */
    public List<WifiSpot> getAllWifiSpots() {
        List<WifiSpot> wifiSpots = new ArrayList<>();
        String sql = "SELECT * FROM wifi_info";

        try (Connection conn = getConnection();
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

    /**
     * 특정 위치 근처의 와이파이 정보 조회 (20개)
     */
    public List<WifiSpot> searchNearbyWifi(double lat, double lnt) {
        List<WifiSpot> wifiSpots = new ArrayList<>();
        String sql = "SELECT *, " +
            "(6371 * acos(cos(radians(?)) * cos(radians(lat)) * " +
            "cos(radians(lnt) - radians(?)) + " +
            "sin(radians(?)) * sin(radians(lat)))) AS distance " +
            "FROM wifi_info " +
            "HAVING distance <= ? " +
            "ORDER BY distance " +
            "LIMIT 20";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.setDouble(3, lat);
            pstmt.setDouble(4, DEFAULT_RADIUS);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    wifiSpots.add(createWifiSpotFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("근처 WiFi 데이터 조회 실패: " + e.getMessage());
            throw new RuntimeException("근처 WiFi 데이터 조회 실패", e);
        }
        return wifiSpots;
    }

    /**
     * 특정 와이파이 정보 조회
     */
    public WifiSpot getWifiSpot(String mgrNo) {
        String sql = "SELECT * FROM wifi_info WHERE x_swifi_mgr_no = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mgrNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createWifiSpotFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("와이파이 정보 조회 실패: " + e.getMessage());
            throw new RuntimeException("와이파이 정보 조회 실패", e);
        }
        return null;
    }

    /**
     * 기존 와이파이 데이터 전체 삭제
     */
    private void clearWifiData() {
        String sql = "DELETE FROM wifi_info";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("데이터 삭제 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("WiFi 데이터 삭제 실패", e);
        }
    }

    /**
     * 위치 히스토리 저장
     */
    public void saveLocationHistory(double lat, double lnt) {
        try {
            History history = new History(lat, lnt);
            historyDao.save(history);  // 이미 선언된 historyDao 사용
            System.out.println("위치 히스토리 저장 완료 - 위도: " + lat + ", 경도: " + lnt);
        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 위치 값: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("위치 히스토리 저장 실패: " + e.getMessage());
            throw new RuntimeException("위치 히스토리 저장 중 오류 발생", e);
        }
    }

    /**
     * 위치 히스토리 목록 조회
     */
    public List<History> getHistoryList() {
        List<History> historyList = new ArrayList<>();
        String sql = "SELECT * FROM location_history ORDER BY search_dttm DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                History history = new History(rs.getDouble("lat"), rs.getDouble("lnt"));
                history.setId(rs.getInt("id"));
                history.setSearchDttm(rs.getTimestamp("search_dttm"));
                historyList.add(history);
            }
        } catch (SQLException e) {
            throw new RuntimeException("위치 히스토리 조회 실패", e);
        }
        return historyList;
    }

    /**
     * 위치 히스토리 삭제
     */
    public void delete(int id) {
        String sql = "DELETE FROM location_history WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("위치 히스토리 삭제 실패", e);
        }
    }

    /**
     * 데이터베이스 연결 획득
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
