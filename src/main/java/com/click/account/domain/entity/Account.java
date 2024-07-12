package com.click.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "ACCOUNT_PASSWORD")
    private String accountPassword;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACC_DAILY_LIMIT")
    private Long accountDailyLimit;

    @Column(name = "ACC_ONE_TIME_LIMIT")
    private Long accountOneTimeLimit;

    @Column(name = "MONYE_AMOUNT")
    private Long moneyAmount;

    @Column(name = "GROUP_ACCOUNT_CODE")
    private String groupAccountCode;

    @Column(name = "ACCOUNT_DISABLE")
    @Setter
    private Boolean accountDisable;

//    public static voi
}
