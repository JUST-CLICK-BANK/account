package com.click.account.domain.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "ACCOUNT")
    private String account;

    @OneToMany(mappedBy = "account")
    private List<GroupAccountMember> groupAccountMembers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "ACCOUNT_PASSWORD")
    private String accountPassword;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACC_DAILY_LIMIT")
    private Long accountDailyLimit;

    @Column(name = "ACC_ONE_TIME_LIMIT")
    private Long accountOneTimeLimit;

    @Column(name = "MONEY_AMOUNT", columnDefinition = "bigint default 0")
    @Builder.Default
    private Long moneyAmount = 0L;

    @Column(name = "GROUP_ACCOUNT_CODE")
    private String groupAccountCode;

    @Column(name = "ACCOUNT_TYPE")
    private Integer type;

    @Column(name = "ACCOUNT_ABLE")
    @Setter
    private Boolean accountAble;

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
    public Account copyToFriend(User friend){
        return Account.builder()
            .account(account)
            .user(friend)
            .moneyAmount(moneyAmount)
            .accountDailyLimit(accountDailyLimit)
            .accountName(accountName)
            .accountPassword(accountPassword)
            .groupAccountCode(groupAccountCode)
            .groupAccountMembers(groupAccountMembers)
            .accountDisable(accountDisable)
            .accountOneTimeLimit(accountOneTimeLimit)
            .build();
    }
}
