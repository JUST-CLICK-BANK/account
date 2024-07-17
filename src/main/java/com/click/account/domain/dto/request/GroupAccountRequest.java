package com.click.account.domain.dto.request;

import com.click.account.domain.entity.GroupAccount;

import java.util.UUID;

public record GroupAccountRequest(
        UUID userId,
        String userNickName,
        String userPofileImg
) {
    public GroupAccount toEntity(String account) {
        return GroupAccount.builder()
                .userId(userId)
                .userNickName(userNickName)
                .userPofileImg(userPofileImg)
                .admin(true)
                .account(account)
                .build();
    }
}
