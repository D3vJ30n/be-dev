import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    // 테스트용 데이터 클래스
    private static class TestData {
        private String message;

        public TestData() {
            this.message = "test";
        }

        public String getMessage() {
            return message;
        }
    }

    // Jackson 테스트
    @Test
    void jacksonTest() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TestData data = new TestData();
            String json = mapper.writeValueAsString(data);
            assertEquals("{\"message\":\"test\"}", json);
        } catch (Exception e) {
            fail("Jackson 테스트 실패: " + e.getMessage());
        }
    }

    // MySQL 연결 테스트
    @Test
    void mysqlTest() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.219.101:3306/kobis_db",  // 실제 IP 주소와 포트
                "kobis_user",                                  // 실제 사용자명
                "kobis1234"                                     // 실제 비밀번호
            );
            assertNotNull(conn);
            conn.close();
        } catch (Exception e) {
            fail("MySQL 연결 실패: " + e.getMessage());
        }
    }

    // Lombok 테스트
    @Test
    void lombokTest() {
        TestData data = new TestData();
        assertEquals("test", data.getMessage());
    }
}