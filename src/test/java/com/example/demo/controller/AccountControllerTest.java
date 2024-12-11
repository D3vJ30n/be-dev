package com.example.demo.controller;

import com.example.demo.domain.Account;
import com.example.demo.domain.AccountStatus;
import com.example.demo.service.AccountService;
import com.example.demo.service.RedisTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import({AccountService.class, RedisTestService.class}) // 필요한 의존성 수동 등록
@ActiveProfiles("test") // 테스트 전용 프로파일 활성화
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RedisTestService redisTestService;

    @Test
    void successGetAccount() throws Exception {
        // given
        given(accountService.getAccount(anyLong()))
            .willReturn(Account.builder()
                .accountNumber("3456")
                .accountStatus(AccountStatus.IN_USE)
                .build());

        // when
        mockMvc.perform(get("/account/876"))
            .andDo(print())
            // then
            .andExpect(jsonPath("$.accountNumber").value("3456"))
            .andExpect(jsonPath("$.accountStatus").value("IN_USE"))
            .andExpect(status().isOk());
    }
}
