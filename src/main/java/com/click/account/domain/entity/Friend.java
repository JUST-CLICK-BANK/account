package com.click.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "FRIENDS")
public class Friend {
    @Id
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "USER_IMG")
    private String userImg;

    @Column(name = "USER_NAME")
    private String userName;
}
