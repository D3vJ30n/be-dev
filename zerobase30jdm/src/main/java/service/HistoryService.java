package service;

import model.History;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    // 히스토리 목록 조회
    public List<History> getHistoryList() {
        List<History> historyList = new ArrayList<>();
        String sql = "SELECT * FROM LOCATION_HISTORY ORDER BY ID DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                History history = new History();
                history.setId(rs.getInt("ID"));
                history.setLat(rs.getDouble("LAT"));
                history.setLnt(rs.getDouble("LNT"));
                history.setSearchDate(rs.getTimestamp("SEARCH_DATE"));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    // 히스토리 추가
    public void insertHistory(History history) {
        String sql = "INSERT INTO LOCATION_HISTORY (LAT, LNT, SEARCH_DATE) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, history.getLat());
            pstmt.setDouble(2, history.getLnt());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 히스토리 삭제
    public void deleteHistory(int id) {
        String sql = "DELETE FROM LOCATION_HISTORY WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 히스토리 단일 조회
    public History getHistory(int id) {
        String sql = "SELECT * FROM LOCATION_HISTORY WHERE ID = ?";
        History history = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    history = new History();
                    history.setId(rs.getInt("ID"));
                    history.setLat(rs.getDouble("LAT"));
                    history.setLnt(rs.getDouble("LNT"));
                    history.setSearchDate(rs.getTimestamp("SEARCH_DATE"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}