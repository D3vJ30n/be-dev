package _test.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://192.168.219.101:3306/testdb1";
        String user = "root";
        String password = "Jdm4568396*";

        System.out.println("DB URL: " + url);
        System.out.println("DB USER: " + user);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("DB 연결 성공!");
        } catch (SQLException e) {
            System.err.println("DB 연결 실패: " + e.getMessage());
        }
    }
}