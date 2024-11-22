package service;

import model.Bookmark;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkService {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    // 북마크 목록 조회
    public List<Bookmark> getBookmarkList() {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT * FROM BOOKMARK ORDER BY REGISTER_DATE DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getInt("ID"));
                bookmark.setGroupId(rs.getInt("GROUP_ID"));
                bookmark.setWifiId(rs.getString("WIFI_ID"));
                bookmark.setWifiName(rs.getString("WIFI_NAME"));
                bookmark.setRegisterDate(rs.getTimestamp("REGISTER_DATE"));
                bookmarkList.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkList;
    }

    // 북마크 추가
    public void insertBookmark(Bookmark bookmark) {
        String sql = "INSERT INTO BOOKMARK (GROUP_ID, WIFI_ID, WIFI_NAME, REGISTER_DATE) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookmark.getGroupId());
            pstmt.setString(2, bookmark.getWifiId());
            pstmt.setString(3, bookmark.getWifiName());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 북마크 삭제
    public void deleteBookmark(int id) {
        String sql = "DELETE FROM BOOKMARK WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 북마크 단일 조회
    public Bookmark getBookmark(int id) {
        String sql = "SELECT * FROM BOOKMARK WHERE ID = ?";
        Bookmark bookmark = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    bookmark = new Bookmark();
                    bookmark.setId(rs.getInt("ID"));
                    bookmark.setGroupId(rs.getInt("GROUP_ID"));
                    bookmark.setWifiId(rs.getString("WIFI_ID"));
                    bookmark.setWifiName(rs.getString("WIFI_NAME"));
                    bookmark.setRegisterDate(rs.getTimestamp("REGISTER_DATE"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmark;
    }
}