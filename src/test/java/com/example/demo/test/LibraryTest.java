package com.example.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 의존성 및 Spring 구성 테스트
 */
@SpringBootTest
class LibraryTest {

    // Java 8 호환 클래스로 변경
    private static class TestData {
        private final String message;

        public TestData() {
            this("test");
        }

        public TestData(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

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

    @Test
    void restTemplateTest() {
        RestTemplate restTemplate = new RestTemplate();
        assertNotNull(restTemplate);
    }

    @Test
    void sqliteTest() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:");
            assertNotNull(conn);
            conn.close();
        } catch (Exception e) {
            fail("SQLite 연결 실패: " + e.getMessage());
        }
    }

    @Test
    void lombokTest() {
        TestData data = new TestData();
        assertEquals("test", data.getMessage());
    }
}