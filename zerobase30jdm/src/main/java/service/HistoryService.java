package service;

import model.History;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {
    // DB 연결 정보
    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    static {
        try {
            // MariaDB JDBC 드라이버 로드
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MariaDB 드라이버 로드 실패", e);
        }
    }

    // 위치 히스토리 목록 조회
    public List<History> getHistoryList() {
        List<History> historyList = new ArrayList<>();
        String sql = "SELECT * FROM location_history ORDER BY search_dttm DESC"; // 위치 히스토리 조회 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) { // 결과를 리스트에 추가
                History history = new History();
                history.setId(rs.getInt("id"));
                history.setLat(rs.getDouble("lat"));
                history.setLnt(rs.getDouble("lnt"));
                history.setSearchDttm(rs.getTimestamp("search_dttm"));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 목록 조회 실패", e); // 예외 처리
        }
        return historyList; // 조회된 히스토리 리스트 반환
    }

    // 위치 히스토리 저장
    public void insertHistory(History history) {
        String sql = "INSERT INTO location_history (lat, lnt, search_dttm) VALUES (?, ?, ?)"; // INSERT 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, history.getLat()); // 위도
            pstmt.setDouble(2, history.getLnt()); // 경도
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // 현재 시간 삽입

            pstmt.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 저장 실패", e); // 예외 처리
        }
    }

    // 위치 히스토리 삭제
    public void deleteHistory(int id) {
        String sql = "DELETE FROM location_history WHERE id = ?"; // DELETE 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id); // 삭제할 히스토리 ID
            int affectedRows = pstmt.executeUpdate(); // 쿼리 실행

            if (affectedRows == 0) {
                throw new SQLException("삭제할 히스토리가 존재하지 않습니다. ID: " + id); // 삭제된 행이 없을 경우 예외 처리
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 삭제 실패", e); // 예외 처리
        }
    }

    // 특정 히스토리 조회
    public History getHistory(int id) {
        String sql = "SELECT * FROM location_history WHERE id = ?"; // 특정 히스토리 조회 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 조회된 히스토리가 있으면
                    History history = new History();
                    history.setId(rs.getInt("id"));
                    history.setLat(rs.getDouble("lat"));
                    history.setLnt(rs.getDouble("lnt"));
                    history.setSearchDttm(rs.getTimestamp("search_dttm"));
                    return history; // 조회된 히스토리 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 조회 실패", e); // 예외 처리
        }
        return null; // 조회된 히스토리가 없으면 null 반환
    }

    // 전체 히스토리 개수 조회
    public int getHistoryCount() {
        String sql = "SELECT COUNT(*) as count FROM location_history"; // 전체 히스토리 개수 조회 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("count"); // 개수 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 개수 조회 실패", e); // 예외 처리
        }
        return 0; // 조회된 개수가 없으면 0 반환
    }

    // 히스토리 전체 삭제
    public void deleteAllHistory() {
        String sql = "DELETE FROM location_history"; // 모든 히스토리 삭제 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("히스토리 전체 삭제 실패", e); // 예외 처리
        }
    }
}
