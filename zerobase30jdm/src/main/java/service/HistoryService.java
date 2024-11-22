package service;

import model.History;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {
    // DB 연결 정보
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MariaDB 드라이버 로드 실패", e);
        }
    }

    // 히스토리 목록 조회
    public List<History> getHistoryList() {
        List<History> historyList = new ArrayList<>();
        String sql = "SELECT * FROM location_history ORDER BY search_dttm DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                History history = new History();
                history.setId(rs.getInt("id"));
                history.setLat(rs.getDouble("lat"));
                history.setLnt(rs.getDouble("lnt"));
                history.setSearchDttm(rs.getTimestamp("search_dttm"));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 목록 조회 실패", e);
        }
        return historyList;
    }

    // 히스토리 저장
    public void insertHistory(History history) {
        String sql = "INSERT INTO location_history (lat, lnt, search_dttm) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, history.getLat());
            pstmt.setDouble(2, history.getLnt());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 저장 실패", e);
        }
    }

    // 히스토리 삭제
    public void deleteHistory(int id) {
        String sql = "DELETE FROM location_history WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("삭제할 히스토리가 존재하지 않습니다. ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 삭제 실패", e);
        }
    }

    // 특정 히스토리 조회
    public History getHistory(int id) {
        String sql = "SELECT * FROM location_history WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    History history = new History();
                    history.setId(rs.getInt("id"));
                    history.setLat(rs.getDouble("lat"));
                    history.setLnt(rs.getDouble("lnt"));
                    history.setSearchDttm(rs.getTimestamp("search_dttm"));
                    return history;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 조회 실패", e);
        }
        return null;
    }

    // 전체 히스토리 개수 조회
    public int getHistoryCount() {
        String sql = "SELECT COUNT(*) as count FROM location_history";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 개수 조회 실패", e);
        }
        return 0;
    }

    // 히스토리 전체 삭제
    public void deleteAllHistory() {
        String sql = "DELETE FROM location_history";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 전체 삭제 실패", e);
        }
    }
}