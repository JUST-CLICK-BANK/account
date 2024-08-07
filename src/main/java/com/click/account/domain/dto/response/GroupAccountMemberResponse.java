package com.click.account.domain.dto.response;

import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;

public record GroupAccountMemberResponse(
    User user
) {
    public static GroupAccountMemberResponse from(User user) {
        return new GroupAccountMemberResponse(
            User.builder()
                .userCode(user.getUserCode())
                .userPorfileImg(user.getUserPorfileImg())
                .userNickName(user.getUserNickName())
                .build());
    }
}
