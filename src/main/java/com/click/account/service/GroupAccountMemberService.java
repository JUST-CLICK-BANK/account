package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.group.GroupAccountMemberRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.domain.entity.Account;
import java.util.List;

public interface GroupAccountMemberService {
    void save(TokenInfo tokenInfo);
    void saveWaitingMember(TokenInfo tokenInfo, String account, List<GroupAccountMemberRequest> requests);
    List<GroupAccountMemberResponse> acceptGroupAccountMember(TokenInfo tokenInfo);
    List<GroupAccountMemberResponse> getGroupAccountMember(Account account);
    void delete(TokenInfo tokenInfo, String account);
}
