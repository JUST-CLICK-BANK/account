package com.click.account.domain.dto.request;

import com.click.account.domain.entity.GroupAccountMember;

import java.util.UUID;

public record GroupAccountMemberRequest(
        UUID userId,
        String userCode,
        String userNickName,
        String userPofileImg,
        String account
) {
    public GroupAccountMember toEntity(String account, Boolean checkAdmin) {
        return GroupAccountMember.builder()
                .userId(userId)
                .userCode(userCode)
                .userNickName(userNickName)
                .userPofileImg(userPofileImg)
                .admin(checkAdmin)
                .account(account)
                .build();
    }
}
