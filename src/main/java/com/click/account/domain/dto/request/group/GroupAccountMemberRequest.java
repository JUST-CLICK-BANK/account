package com.click.account.domain.dto.request.group;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GroupAccountMemberRequest(
    UUID id,
    String code,
    String img,
    String name
) {
    public List<GroupAccountMember> toEntities(Account account, String code) {
        List<GroupAccountMember> groupAccountMembers = new ArrayList<>();
        groupAccountMembers.add(GroupAccountMember.builder()
            .userId(id)
            .userCode(code)
            .userPofileImg(img)
            .userNickName(name)
            .admin(false)
            .status(false)
            .inviteCode(code)
            .account(account)
            .build()
        );
        return groupAccountMembers;
    }
}
