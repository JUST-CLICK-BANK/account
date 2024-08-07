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
@Table(name = "FRIENDS")
public class Friend {
    @Id
    @Column(name = "FRIEND_ID")
    private UUID friendId;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "FRIEND_CODE")
    private String friendCode;

    @Column(name = "FRIEND_IMG")
    private String friendImg;

    @Column(name = "FRIEND_RANK")
    private Integer friendRank;

    @Column(name = "FRIEND_NAME")
    private String friendName;
}
