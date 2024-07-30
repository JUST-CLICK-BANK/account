package com.click.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "USER_NICK_NAME")
    private String userNickName;

    @Column(name = "USER_PROFILE_IMG")
    private String userPorfileImg;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "USER_CREDIT_RANK")
    private Integer rank;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
}
