package com.click.account.domain.dto.request.group;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.repository.query.Param;

public record GroupAccountMemberRequest(
    String id,
    String code,
    String img,
    String name,
    Integer rank
) {
    public GroupAccountMember toEntity(Account account, User user) {
        return GroupAccountMember.builder()
            .user(user)
            .admin(false)
            .status(false)
            .account(account)
            .inviteCode(user.getUserCode())
            .build();
    }

    public static List<GroupAccountMember> toEntities(List<GroupAccountMemberRequest> requests, Account account, List<User> users) {
        List<GroupAccountMember> groupAccountMembers = new ArrayList<>();
        for (GroupAccountMemberRequest request : requests) {
            users.forEach(user ->
                groupAccountMembers.add(request.toEntity(account, user))
            );
        }
        return groupAccountMembers;
    }
}
