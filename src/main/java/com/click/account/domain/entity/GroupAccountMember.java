package com.click.account.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
import org.apache.kafka.common.protocol.types.Field.Bool;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "GROUP_ACCOUNT_LISTS")
public class GroupAccountMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRIEND_ID")
    private Long friendId;

    @Column(name = "ADMIN")
    private Boolean admin;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "INVITE_CODE")
    private String inviteCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CODE")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
