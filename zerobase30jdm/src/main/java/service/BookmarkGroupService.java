package service;

import model.BookmarkGroup;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupService {
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

    // 북마크 그룹 목록 조회
    public List<BookmarkGroup> getBookmarkGroupList() {
        List<BookmarkGroup> groupList = new ArrayList<>();
        String sql = "SELECT * FROM bookmark_group ORDER BY order_no";

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
                groupList.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 그룹 목록 조회 실패", e);
        }
        return groupList;
    }

    // 북마크 그룹 추가
    public void insertBookmarkGroup(BookmarkGroup group) {
        String sql = "INSERT INTO bookmark_group (name, order_no, reg_dttm) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrderNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int result = pstmt.executeUpdate();
            if (result != 1) {
                throw new SQLException("북마크 그룹 추가 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 그룹 추가 실패", e);
        }
    }

    // 북마크 그룹 수정
    public void updateBookmarkGroup(BookmarkGroup group) {
        String sql = "UPDATE bookmark_group SET name = ?, order_no = ?, upd_dttm = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrderNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(4, group.getId());

            int result = pstmt.executeUpdate();
            if (result != 1) {
                throw new SQLException("수정할 북마크 그룹이 존재하지 않습니다. ID: " + group.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 그룹 수정 실패", e);
        }
    }

    // 북마크 그룹 삭제 (연관된 북마크도 함께 삭제)
    public void deleteBookmarkGroup(int id) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 먼저 해당 그룹의 북마크들을 삭제
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
                if (result != 1) {
                    throw new SQLException("삭제할 북마크 그룹이 존재하지 않습니다. ID: " + id);
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
            throw new RuntimeException("북마크 그룹 삭제 실패", e);
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

    // 특정 북마크 그룹 조회
    public BookmarkGroup getBookmarkGroup(int id) {
        String sql = "SELECT * FROM bookmark_group WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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
            throw new RuntimeException("북마크 그룹 조회 실패", e);
        }
        return null;
    }

    // 순서 중복 체크
    public boolean isOrderNoExists(int orderNo, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM bookmark_group WHERE order_no = ? AND id != ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderNo);
            pstmt.setInt(2, excludeId != null ? excludeId : -1);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("순서 중복 체크 실패", e);
        }
        return false;
    }
}