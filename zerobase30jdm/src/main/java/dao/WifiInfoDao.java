package dao;

import model.WifiInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiInfoDao {

    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    /**
     * DB에 저장된 모든 와이파이 정보를 가져오는 메서드
     */
    public List<WifiInfo> getAllWifiSpots() {
        List<WifiInfo> wifiSpots = new ArrayList<>();
        String sql = "SELECT * FROM wifi_info";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                WifiInfo spot = new WifiInfo(
                    rs.getString("x_swifi_mgr_no"),
                    rs.getString("x_swifi_wrdofc"),
                    rs.getString("x_swifi_main_nm"),
                    rs.getString("x_swifi_adres1"),
                    rs.getString("x_swifi_adres2"),
                    rs.getString("x_swifi_instl_floor"),
                    rs.getString("x_swifi_instl_ty"),
                    rs.getString("x_swifi_instl_mby"),
                    rs.getString("x_swifi_svc_se"),
                    rs.getString("x_swifi_cmcwr"),
                    rs.getString("x_swifi_cnstc_year"),
                    rs.getString("x_swifi_inout_door"),
                    rs.getString("x_swifi_remars3"),
                    rs.getDouble("lat"),
                    rs.getDouble("lnt"),
                    rs.getString("work_dttm")
                );
                wifiSpots.add(spot);
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 조회 중 오류 발생", e);
        }

        return wifiSpots;
    }

    /**
     * DB의 모든 와이파이 데이터를 삭제
     */
    public void clearWifiData() {
        String sql = "DELETE FROM wifi_info";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("WiFi 데이터 삭제 실패", e);
        }
    }

    /**
     * 와이파이 데이터를 DB에 저장
     */
    public void saveWifiData(List<WifiInfo> wifiInfos) {
        String sql = "INSERT INTO wifi_info (x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, " +
            "x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor, x_swifi_instl_ty, " +
            "x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, x_swifi_cnstc_year, " +
            "x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (WifiInfo wifiInfo : wifiInfos) {
                pstmt.setString(1, wifiInfo.getMgrNo());
                pstmt.setString(2, wifiInfo.getBorough());
                pstmt.setString(3, wifiInfo.getName());
                pstmt.setString(4, wifiInfo.getAddress1());
                pstmt.setString(5, wifiInfo.getAddress2());
                pstmt.setString(6, wifiInfo.getInstallFloor());
                pstmt.setString(7, wifiInfo.getInstallType());
                pstmt.setString(8, wifiInfo.getInstallAgency());
                pstmt.setString(9, wifiInfo.getServiceType());
                pstmt.setString(10, wifiInfo.getNetworkType());
                pstmt.setString(11, wifiInfo.getInstallYear());
                pstmt.setString(12, wifiInfo.getIndoorOutdoor());
                pstmt.setString(13, wifiInfo.getRemarks());
                pstmt.setDouble(14, wifiInfo.getLatitude());
                pstmt.setDouble(15, wifiInfo.getLongitude());
                pstmt.setString(16, wifiInfo.getWorkDateTime());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("WiFi 데이터 저장 실패", e);
        }
    }

    /**
     * 특정 위치(lat, lng) 기준으로 가까운 와이파이를 검색
     */
    public List<WifiInfo> findNearbyWifi(double lat, double lng) {
        List<WifiInfo> wifiSpots = new ArrayList<>();
        String sql = "SELECT *, " +
            "(6371 * acos(cos(radians(?)) * cos(radians(lat)) * " +
            "cos(radians(lnt) - radians(?)) + sin(radians(?)) * sin(radians(lat)))) AS distance " +
            "FROM wifi_info " +
            "HAVING distance <= 2 " + // 2km 이내의 와이파이만 검색
            "ORDER BY distance " +
            "LIMIT 20";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lng);
            pstmt.setDouble(3, lat);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WifiInfo spot = new WifiInfo(
                        rs.getString("x_swifi_mgr_no"),
                        rs.getString("x_swifi_wrdofc"),
                        rs.getString("x_swifi_main_nm"),
                        rs.getString("x_swifi_adres1"),
                        rs.getString("x_swifi_adres2"),
                        rs.getString("x_swifi_instl_floor"),
                        rs.getString("x_swifi_instl_ty"),
                        rs.getString("x_swifi_instl_mby"),
                        rs.getString("x_swifi_svc_se"),
                        rs.getString("x_swifi_cmcwr"),
                        rs.getString("x_swifi_cnstc_year"),
                        rs.getString("x_swifi_inout_door"),
                        rs.getString("x_swifi_remars3"),
                        rs.getDouble("lat"),
                        rs.getDouble("lnt"),
                        rs.getString("work_dttm")
                    );
                    wifiSpots.add(spot);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("근처 와이파이 검색 중 오류 발생", e);
        }

        return wifiSpots;
    }
}
