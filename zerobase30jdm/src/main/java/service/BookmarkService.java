package service;

import model.Bookmark;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkService {
    // 데이터베이스 연결에 필요한 URL, 사용자명, 비밀번호
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // MariaDB 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MariaDB 드라이버 로드 실패", e); // 드라이버 로드 실패 시 예외 처리
        }
    }

    // 북마크 목록 조회 (그룹명과 와이파이명 포함)
    public List<Bookmark> getBookmarkList() {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT b.*, bg.name as group_name, w.main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.mgr_no " +
            "ORDER BY b.reg_dttm DESC"; // 정렬: 등록일시 내림차순

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
                bookmarkList.add(bookmark); // 결과 리스트에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 목록 조회 실패", e); // 예외 처리
        }
        return bookmarkList;
    }

    // 북마크 추가
    public void insertBookmark(Bookmark bookmark) {
        String sql = "INSERT INTO bookmark (group_id, wifi_mgr_no, reg_dttm) VALUES (?, ?, ?)"; // INSERT 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookmark.getGroupId());
            pstmt.setString(2, bookmark.getWifiMgrNo());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // 현재 시간 삽입

            int result = pstmt.executeUpdate();
            if (result != 1) { // 삽입된 레코드가 하나가 아니면 오류
                throw new SQLException("북마크 추가 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 추가 실패", e); // 예외 처리
        }
    }

    // 북마크 삭제
    public void deleteBookmark(int id) {
        String sql = "DELETE FROM bookmark WHERE id = ?"; // DELETE 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();

            if (result != 1) { // 삭제할 레코드가 하나가 아니면 오류
                throw new SQLException("삭제할 북마크가 존재하지 않습니다. ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 삭제 실패", e); // 예외 처리
        }
    }

    // 특정 북마크 조회
    public Bookmark getBookmark(int id) {
        String sql = "SELECT b.*, bg.name as group_name, w.main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.mgr_no " +
            "WHERE b.id = ?"; // 특정 북마크 조회 쿼리

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 조회된 데이터가 있으면
                    Bookmark bookmark = new Bookmark();
                    bookmark.setId(rs.getInt("id"));
                    bookmark.setGroupId(rs.getInt("group_id"));
                    bookmark.setWifiMgrNo(rs.getString("wifi_mgr_no"));
                    bookmark.setRegDttm(rs.getTimestamp("reg_dttm"));
                    bookmark.setGroupName(rs.getString("group_name"));
                    bookmark.setWifiName(rs.getString("wifi_name"));
                    return bookmark; // 조회된 북마크 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("북마크 조회 실패", e); // 예외 처리
        }
        return null; // 해당 북마크가 없으면 null 반환
    }

    // 특정 그룹의 북마크 목록 조회
    public List<Bookmark> getBookmarkListByGroupId(int groupId) {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT b.*, bg.name as group_name, w.main_nm as wifi_name " +
            "FROM bookmark b " +
            "LEFT JOIN bookmark_group bg ON b.group_id = bg.id " +
            "LEFT JOIN wifi_info w ON b.wifi_mgr_no = w.mgr_no " +
            "WHERE b.group_id = ? " +
            "ORDER BY b.reg_dttm DESC"; // 그룹별 북마크 목록 조회

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, groupId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) { // 조회된 결과를 리스트에 추가
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
            throw new RuntimeException("그룹별 북마크 목록 조회 실패", e); // 예외 처리
        }
        return bookmarkList;
    }
}
