package dao;

import model.BookmarkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupDao {
    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Jdm4568396*";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MariaDB 드라이버 로드 실패", e);
        }
    }

    // 즐겨찾기 그룹 목록 조회
    public List<BookmarkGroup> getBookmarkGroupList() {
        List<BookmarkGroup> groups = new ArrayList<>();
        String sql = "SELECT * FROM bookmark_group ORDER BY order_no ASC";

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
                groups.add(group);
            }
        } catch (SQLException e) {
            throw new RuntimeException("즐겨찾기 그룹 목록 조회 중 오류 발생", e);
        }

        return groups;
    }

    // 즐겨찾기 그룹 추가
    public void insertBookmarkGroup(BookmarkGroup group) {
        String sql = "INSERT INTO bookmark_group (name, order_no, reg_dttm) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrderNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("즐겨찾기 그룹 추가 중 오류 발생", e);
        }
    }

    // 즐겨찾기 그룹 수정
    public void updateBookmarkGroup(BookmarkGroup group) {
        String sql = "UPDATE bookmark_group SET name = ?, order_no = ?, upd_dttm = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrderNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(4, group.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("수정할 데이터가 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("즐겨찾기 그룹 수정 중 오류 발생", e);
        }
    }

    // 즐겨찾기 그룹 삭제
    public void deleteBookmarkGroup(int id) {
        String sql = "DELETE FROM bookmark_group WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("삭제할 데이터가 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("즐겨찾기 그룹 삭제 중 오류 발생", e);
        }
    }

    // 특정 즐겨찾기 그룹 조회
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
            throw new RuntimeException("즐겨찾기 그룹 조회 중 오류 발생", e);
        }

        return null;
    }

    // 순서 중복 확인
    public boolean isOrderNoExists(int orderNo, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM bookmark_group WHERE order_no = ?";

        if (excludeId != null) {
            sql += " AND id != ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderNo);
            if (excludeId != null) {
                pstmt.setInt(2, excludeId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("순서 중복 확인 중 오류 발생", e);
        }

        return false;
    }
}