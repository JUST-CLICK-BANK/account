package com.click.account.domain.dto.response;

import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import java.util.List;

public record GroupAccountMemberResponse(
    String accountName
) {
    public static GroupAccountMemberResponse from(String accountName) {
        return new GroupAccountMemberResponse(
            accountName
        );
    }
}
