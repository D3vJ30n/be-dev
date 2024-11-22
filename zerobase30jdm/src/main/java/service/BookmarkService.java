package service;

import model.Bookmark;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkService {
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

    // 북마크 목록 조회 (그룹명과 와이파이명 포함)
    public List<Bookmark> getBookmarkList() {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT b.*, bg.name as group_name, w.main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.mgr_no " +
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
            throw new RuntimeException("북마크 목록 조회 실패", e);
        }
        return bookmarkList;
    }

    // 북마크 추가
    public void insertBookmark(Bookmark bookmark) {
        String sql = "INSERT INTO bookmark (group_id, wifi_mgr_no, reg_dttm) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookmark.getGroupId());
            pstmt.setString(2, bookmark.getWifiMgrNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int result = pstmt.executeUpdate();
            if (result != 1) {
                throw new SQLException("북마크 추가 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 추가 실패", e);
        }
    }

    // 북마크 삭제
    public void deleteBookmark(int id) {
        String sql = "DELETE FROM bookmark WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();

            if (result != 1) {
                throw new SQLException("삭제할 북마크가 존재하지 않습니다. ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 삭제 실패", e);
        }
    }

    // 특정 북마크 조회
    public Bookmark getBookmark(int id) {
        String sql = "SELECT b.*, bg.name as group_name, w.main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.mgr_no " +
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
            throw new RuntimeException("북마크 조회 실패", e);
        }
        return null;
    }

    // 특정 그룹의 북마크 목록 조회
    public List<Bookmark> getBookmarkListByGroupId(int groupId) {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT b.*, bg.name as group_name, w.main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.mgr_no " +
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
            throw new RuntimeException("그룹별 북마크 목록 조회 실패", e);
        }
        return bookmarkList;
    }
}