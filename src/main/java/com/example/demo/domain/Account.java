package com.example.demo.domain;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 전략으로 기본 키 자동 생성
    private Long id;

    @Column(nullable = false, unique = true) // accountNumber는 필수이며 중복 불가
    private String accountNumber;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false) // accountStatus는 필수
    private AccountStatus accountStatus;
}
