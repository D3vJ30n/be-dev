package com.example.demo.controller;

import com.example.demo.domain.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.RedisTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AccountController { // 계좌 컨트롤러 클래스

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class); // 로거 초기화

    private final AccountService accountService;
    private final RedisTestService redisTestService;

    // 생성자 주입
    public AccountController(AccountService accountService, RedisTestService redisTestService) {
        this.accountService = accountService;
        this.redisTestService = redisTestService;
    }

    @GetMapping("/get-lock") // Redis 락 획득을 위한 엔드포인트
    public String getLock() {
        logger.info("Attempting to acquire Redis lock");
        try {
            String result = redisTestService.getLock();
            logger.info("Redis lock result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Failed to acquire Redis lock", e);
            return "Error acquiring lock";
        }
    }

    @GetMapping("/create-account") // 계좌 생성을 위한 엔드포인트
    public String createAccount() {
        logger.info("Creating a new account");
        try {
            accountService.createAccount();
            logger.info("Account created successfully");
            return "success";
        } catch (Exception e) {
            logger.error("Failed to create account", e);
            return "Error creating account";
        }
    }

    @GetMapping("/account/{id}") // 계좌 조회를 위한 엔드포인트
    public Account getAccount(@PathVariable Long id) {
        logger.info("Fetching account with id: {}", id);
        try {
            Account account = accountService.getAccount(id);
            logger.info("Account fetched: {}", account);
            return account;
        } catch (Exception e) {
            logger.error("Failed to fetch account", e);
            throw new RuntimeException("Account not found with id: " + id, e);
        }
    }
}
