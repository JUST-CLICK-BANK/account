package com.click.account.domain.dto.response;

import com.click.account.domain.entity.GroupAccountMember;

public record GroupAccountMemberResponse(
    String userName,
    String userImg,
    String userCode,
    Boolean admin
) {
    public static GroupAccountMemberResponse from(GroupAccountMember groupAccountMember) {
        return new GroupAccountMemberResponse(
            groupAccountMember.getUserNickName(),
            groupAccountMember.getUserPofileImg(),
            groupAccountMember.getUserCode(),
            groupAccountMember.getAdmin()
        );
    }
}
