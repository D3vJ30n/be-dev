package service;

import model.Bookmark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkService {
    private static final String DB_URL = "jdbc:mariadb://192.168.219.101:3306/testdb1?useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Jdm4568396*";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // MariaDB 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MariaDB 드라이버 로드 실패", e);
        }
    }

    // 북마크 목록 조회 (그룹명과 와이파이명 포함)
    public List<Bookmark> getBookmarkList() {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT b.*, bg.name as group_name, w.x_swifi_main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.x_swifi_mgr_no " +
            "ORDER BY b.reg_dttm DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getInt("id"));
                bookmark.setGroupId(rs.getInt("group_id"));
                bookmark.setWifiMgrNo(rs.getString("wifi_mgr_no"));
                bookmark.setRegDttm(rs.getTimestamp("reg_dttm"));
                bookmark.setGroupName(rs.getString("group_name"));
                bookmark.setWifiName(rs.getString("wifi_name"));
                bookmarkList.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 목록 조회 실패", e);
        }
        return bookmarkList;
    }

    // 북마크 추가
    public void insertBookmark(Bookmark bookmark) {
        String checkGroupSql = "SELECT COUNT(*) FROM bookmark_group WHERE id = ?";
        String checkWifiSql = "SELECT COUNT(*) FROM wifi_info WHERE x_swifi_mgr_no = ?";
        String insertSql = "INSERT INTO bookmark (group_id, wifi_mgr_no, reg_dttm) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 그룹 ID 확인
            try (PreparedStatement checkGroupStmt = conn.prepareStatement(checkGroupSql)) {
                checkGroupStmt.setInt(1, bookmark.getGroupId());
                try (ResultSet rs = checkGroupStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        throw new SQLException("유효하지 않은 그룹 ID: " + bookmark.getGroupId());
                    }
                }
            }

            // 와이파이 관리 번호 확인
            try (PreparedStatement checkWifiStmt = conn.prepareStatement(checkWifiSql)) {
                checkWifiStmt.setString(1, bookmark.getWifiMgrNo());
                try (ResultSet rs = checkWifiStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        throw new SQLException("유효하지 않은 와이파이 관리 번호: " + bookmark.getWifiMgrNo());
                    }
                }
            }

            // 즐겨찾기 추가
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, bookmark.getGroupId());
                insertStmt.setString(2, bookmark.getWifiMgrNo());
                insertStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 추가 실패: " + e.getMessage(), e);
        }
    }

    // 즐겨찾기 삭제
    public void deleteBookmark(int id) {
        Bookmark bookmark = getBookmark(id);
        if (bookmark == null) {
            throw new IllegalArgumentException("삭제할 즐겨찾기가 존재하지 않습니다. ID: " + id);
        }

        String sql = "DELETE FROM bookmark WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();

            if (result != 1) {
                throw new SQLException("삭제할 즐겨찾기가 존재하지 않습니다. ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 삭제 실패", e);
        }
    }

    // 특정 즐겨찾기 조회
    public Bookmark getBookmark(int id) {
        String sql = "SELECT b.*, bg.name as group_name, w.x_swifi_main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.x_swifi_mgr_no " +
            "WHERE b.id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setId(rs.getInt("id"));
                    bookmark.setGroupId(rs.getInt("group_id"));
                    bookmark.setWifiMgrNo(rs.getString("wifi_mgr_no"));
                    bookmark.setRegDttm(rs.getTimestamp("reg_dttm"));
                    bookmark.setGroupName(rs.getString("group_name"));
                    bookmark.setWifiName(rs.getString("wifi_name"));
                    return bookmark;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("즐겨찾기 조회 실패", e);
        }
        return null;
    }

    // 특정 그룹의 즐겨찾기 목록 조회
    public List<Bookmark> getBookmarkListByGroupId(int groupId) {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT b.*, bg.name as group_name, w.x_swifi_main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.x_swifi_mgr_no " +
            "WHERE b.group_id = ? " +
            "ORDER BY b.reg_dttm DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, groupId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setId(rs.getInt("id"));
                    bookmark.setGroupId(rs.getInt("group_id"));
                    bookmark.setWifiMgrNo(rs.getString("wifi_mgr_no"));
                    bookmark.setRegDttm(rs.getTimestamp("reg_dttm"));
                    bookmark.setGroupName(rs.getString("group_name"));
                    bookmark.setWifiName(rs.getString("wifi_name"));
                    bookmarkList.add(bookmark);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("그룹별 즐겨찾기 목록 조회 실패", e);
        }
        return bookmarkList;
    }
}

