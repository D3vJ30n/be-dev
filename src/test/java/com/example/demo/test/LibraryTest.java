package com.example.demo.test;

import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 라이브러리 테스트
 */
//@SpringBootTest
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
}