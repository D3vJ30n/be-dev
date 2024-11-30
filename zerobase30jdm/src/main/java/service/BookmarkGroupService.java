package service;

import model.BookmarkGroup;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupService {
    // 데이터베이스 연결에 필요한 URL, 사용자명, 비밀번호
    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1?useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Jdm4568396*";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // MariaDB 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MariaDB 드라이버 로드 실패", e); // 드라이버 로드 실패 시 예외 처리
        }
    }

    // 즐겨찾기 그룹 목록 조회
    public List<BookmarkGroup> getBookmarkGroupList() {
        List<BookmarkGroup> groupList = new ArrayList<>();
        String sql = "SELECT * FROM bookmark_group ORDER BY order_no"; // 그룹 순서(order_no)대로 조회

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BookmarkGroup group = new BookmarkGroup();
                group.setId(rs.getInt("id"));
                group.setName(rs.getString("name"));
                group.setOrderNo(rs.getInt("order_no"));
                group.setRegDttm(rs.getTimestamp("reg_dttm"));
                group.setUpdDttm(rs.getTimestamp("upd_dttm"));
                groupList.add(group); // 결과 리스트에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 그룹 목록 조회 실패", e); // 예외 처리
        }
        return groupList;
    }

    // 즐겨찾기 그룹 추가
    public void insertBookmarkGroup(BookmarkGroup group) {
        String sql = "INSERT INTO bookmark_group (name, order_no, reg_dttm) VALUES (?, ?, ?)"; // INSERT 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrderNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // 현재 시간 삽입

            int result = pstmt.executeUpdate();
            if (result != 1) { // 삽입된 레코드가 하나가 아니면 오류
                throw new SQLException("즐겨찾기 그룹 추가 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 그룹 추가 실패", e); // 예외 처리
        }
    }

    // 즐겨찾기 그룹 수정
    public void updateBookmarkGroup(BookmarkGroup group) {
        String sql = "UPDATE bookmark_group SET name = ?, order_no = ?, upd_dttm = ? WHERE id = ?"; // UPDATE 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrderNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // 수정일시 갱신
            pstmt.setInt(4, group.getId()); // 수정할 ID

            int result = pstmt.executeUpdate();
            if (result != 1) { // 수정된 레코드가 하나가 아니면 오류
                throw new SQLException("수정할 즐겨찾기 그룹이 존재하지 않습니다. ID: " + group.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 그룹 수정 실패", e); // 예외 처리
        }
    }

    // 즐겨찾기 그룹 삭제 (연관된 즐겨찾기도 함께 삭제)
    public void deleteBookmarkGroup(int id) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 먼저 해당 그룹의 즐겨찾기들을 삭제
            String deleteBookmarksSql = "DELETE FROM bookmark WHERE group_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteBookmarksSql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            // 그룹 삭제
            String deleteGroupSql = "DELETE FROM bookmark_group WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteGroupSql)) {
                pstmt.setInt(1, id);
                int result = pstmt.executeUpdate();
                if (result != 1) { // 삭제할 그룹이 없으면 오류
                    throw new SQLException("삭제할 즐겨찾기 그룹이 존재하지 않습니다. ID: " + id);
                }
            }

            conn.commit(); // 트랜잭션 커밋
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // 오류 발생 시 롤백
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 그룹 삭제 실패", e); // 예외 처리
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 자동 커밋 모드 복원
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 특정 즐겨찾기 그룹 조회
    public BookmarkGroup getBookmarkGroup(int id) {
        String sql = "SELECT * FROM bookmark_group WHERE id = ?"; // 특정 그룹 조회 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 그룹이 존재하면
                    BookmarkGroup group = new BookmarkGroup();
                    group.setId(rs.getInt("id"));
                    group.setName(rs.getString("name"));
                    group.setOrderNo(rs.getInt("order_no"));
                    group.setRegDttm(rs.getTimestamp("reg_dttm"));
                    group.setUpdDttm(rs.getTimestamp("upd_dttm"));
                    return group;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 그룹 조회 실패", e); // 예외 처리
        }
        return null; // 해당 그룹이 없으면 null 반환
    }

    // 순서 중복 체크
    public boolean isOrderNoExists(int orderNo, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM bookmark_group WHERE order_no = ? AND id != ?"; // 순서 중복 체크 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderNo);
            pstmt.setInt(2, excludeId != null ? excludeId : -1); // 제외할 ID가 있으면 제외

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // 중복된 순서가 있으면 true 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("순서 중복 체크 실패", e); // 예외 처리
        }
        return false; // 중복된 순서가 없으면 false 반환
    }
}
