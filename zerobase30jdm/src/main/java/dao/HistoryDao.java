package dao;

import model.History;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoryDao {
    public void save(History history) {
        String sql = "INSERT INTO location_history (lat, lnt, search_dttm) VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, history.getLatitude());
            pstmt.setDouble(2, history.getLongitude());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}