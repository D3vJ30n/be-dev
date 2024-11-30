package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // DB 연결을 위한 메서드
    public static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:mariadb://192.168.219.101:3306/testdb1?useUnicode=true&characterEncoding=UTF-8"; // DB URL
        String dbUser = "root"; // DB 사용자
        String dbPassword = "Jdm4568396*"; // DB 비밀번호

        try {
            // MariaDB JDBC 드라이버 로딩
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("DB 연결 실패");
        }
    }
}
