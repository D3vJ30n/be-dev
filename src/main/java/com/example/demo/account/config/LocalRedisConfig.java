package com.example.demo.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import jakarta.annotation.PreDestroy;

@Configuration // 로컬 Redis 설정 클래스
public class LocalRedisConfig { // 로컬 Redis 설정 클래스

    @Value("${spring.redis.port}") // Redis 포트 번호 설정
    private int redisPort; // Redis 포트 번호 변수

    private RedisServer redisServer; // Redis 서버 인스턴스

    @Bean
    public RedisServer redisServer() { // Redis 서버 빈 생성
        redisServer = new RedisServer(redisPort); // Redis 서버 생성
        redisServer.start(); // Redis 서버 시작
        return redisServer; // Redis 서버 반환
    }

    @PreDestroy
    public void stopRedis() { // 애플리케이션 종료 시 Redis 서버 종료
        if (redisServer != null) { // Redis 서버가 실행 중인 경우
            redisServer.stop(); // Redis 서버 종료
        }
    }
}
