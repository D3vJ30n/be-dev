package com.example.demo.account.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisRepositoryConfig { // Redis 설정 클래스
    @Value("${spring.redis.host}") // Redis 호스트 설정
    private String redisHost; // Redis 호스트 변수

    @Value("${spring.redis.port}") // Redis 포트 설정
    private int redisPort; // Redis 포트 변수

    @Bean
    public RedissonClient redissonClient() { // Redisson 클라이언트 빈 생성
        Config config = new Config(); // Redisson 설정 객체 생성
        config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort); // Redis 서버 설정

        return Redisson.create(config); // Redisson 클라이언트 생성 및 반환
    }
}
