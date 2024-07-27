package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;

import com.click.account.domain.dto.request.group.GroupAccountMemberRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import java.util.List;
import java.util.UUID;

public interface GroupAccountDao {
    void saveGroupToUser(TokenInfo tokenInfo, String Account, UUID userId);
    void waitGroupAccountUser(List<GroupAccountMember> groupAccountMembers);
    List<GroupAccountMember> getGroupAccountMember(Account account);
    List<GroupAccountMember> getGroupAccountMemberFromUserId(UUID userId);
    void deleteGroupMember(String userCode, Account account);
}
