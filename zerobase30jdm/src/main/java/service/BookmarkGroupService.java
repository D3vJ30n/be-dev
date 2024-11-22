package service;

import model.BookmarkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookmarkGroupService {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "618811";

    private static final Logger logger = Logger.getLogger(BookmarkGroupService.class.getName());

    // 북마크 그룹 목록 조회
    public List<BookmarkGroup> getBookmarkGroupList() {
        List<BookmarkGroup> groupList = new ArrayList<>();
        String sql = "SELECT ID, NAME, GROUP_ORDER, CREATE_DATE, MODIFY_DATE FROM bookmark_group ORDER BY GROUP_ORDER";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BookmarkGroup group = new BookmarkGroup();
                group.setId(rs.getInt("ID"));
                group.setName(rs.getString("NAME"));
                group.setOrder(rs.getInt("GROUP_ORDER"));
                group.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                group.setModifyDate(rs.getTimestamp("MODIFY_DATE"));
                groupList.add(group);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching bookmark groups", e);
        }
        return groupList;
    }

    // 북마크 그룹 추가
    public void insertBookmarkGroup(BookmarkGroup group) {
        String sql = "INSERT INTO bookmark_group (NAME, GROUP_ORDER, CREATE_DATE) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrder());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            logger.log(Level.INFO, "Bookmark group added: {0}", group.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding bookmark group", e);
        }
    }

    // 북마크 그룹 수정
    public void updateBookmarkGroup(BookmarkGroup group) {
        String sql = "UPDATE bookmark_group SET NAME = ?, GROUP_ORDER = ?, MODIFY_DATE = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getOrder());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(4, group.getId());
            pstmt.executeUpdate();
            logger.log(Level.INFO, "Bookmark group updated: ID={0}", group.getId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating bookmark group", e);
        }
    }

    // 북마크 그룹 삭제
    public void deleteBookmarkGroup(int id) {
        String sql = "DELETE FROM bookmark_group WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.log(Level.INFO, "Bookmark group deleted: ID={0}", id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting bookmark group", e);
        }
    }

    // 북마크 그룹 단일 조회
    public BookmarkGroup getBookmarkGroup(int id) {
        String sql = "SELECT ID, NAME, GROUP_ORDER, CREATE_DATE, MODIFY_DATE FROM bookmark_group WHERE ID = ?";
        BookmarkGroup group = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    group = new BookmarkGroup();
                    group.setId(rs.getInt("ID"));
                    group.setName(rs.getString("NAME"));
                    group.setOrder(rs.getInt("GROUP_ORDER"));
                    group.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                    group.setModifyDate(rs.getTimestamp("MODIFY_DATE"));
                    logger.log(Level.INFO, "Bookmark group retrieved: ID={0}", id);
                } else {
                    logger.log(Level.WARNING, "No bookmark group found with ID={0}", id);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving bookmark group", e);
        }
        return group;
    }
}
