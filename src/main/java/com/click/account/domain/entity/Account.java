package com.click.account.domain.entity;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

//    @Column(name = "USER_ID")
//    private UUID userId;

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

    public void updateName(String accountName) {
        this.accountName = accountName;
    }

    public void updateMoney(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void updatePassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public void updateTransferLimit(Long accountDailyLimit, Long accountOneTimeLimit) {
        this.accountDailyLimit = accountDailyLimit;
        this.accountOneTimeLimit = accountOneTimeLimit;
    }
}
