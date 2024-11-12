package com.example.demo;  // 원하는 패키지 이름으로 변경 가능

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Spring Boot 애플리케이션을 시작하는 어노테이션
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);  // 애플리케이션 실행
    }
}