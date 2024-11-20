package _test.test;

import java.sql.*;

public class DBTest {
    public static void main(String[] args) {
        try {
            System.out.println("DB 연결 테스트 시작");
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("드라이버 로드 성공");

            Connection conn = DriverManager.getConnection(
                "jdbc:mariadb://192.168.219.101:3306/testdb1",
                "root",
                "618811"
            );
            System.out.println("연결 성공!");
            conn.close();
        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}