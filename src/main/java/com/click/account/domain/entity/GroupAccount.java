package com.click.account.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "GROUP_ACCOUNT_LISTS")
public class GroupAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRIEND_ID")
    private Long friendId;

    @Column(name = "USER_PROFILE_IMG")
    private String userPofileImg;

    @Column(name = "USER_NICK_NAME")
    private String userNickName;

    @Column(name = "ADMIN")
    private Boolean admin;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "ACCOUNT")
    private String account;
}
