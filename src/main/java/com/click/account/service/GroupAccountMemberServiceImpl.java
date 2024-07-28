package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.group.GroupAccountMemberRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Friend;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.repository.FriendRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupAccountMemberServiceImpl implements GroupAccountMemberService {
    private final FriendRepository friendRepository;
    private final GroupAccountDao groupAccountDao;
    private final AccountDao accountDao;

    @Override
    // 친구 요청 승인 시 모임 통장에 저장할 로직
    public void save(TokenInfo tokenInfo) {
        Friend friend = friendRepository.findById(UUID.fromString(tokenInfo.id())).orElseThrow(IllegalArgumentException::new);
        groupAccountDao.saveGroupToUser(tokenInfo, friend.getAccount(), UUID.fromString(tokenInfo.id()));
    }

    @Override
    public void saveWaitingMember(TokenInfo tokenInfo, String reqAccount, List<GroupAccountMemberRequest> requests) {
        Account account = accountDao.getAccount(reqAccount);
        List<GroupAccountMember> groupAccountMembers = requests.stream()
            .flatMap(request -> request.toEntities(account).stream())
            .toList();
        groupAccountDao.waitGroupAccountUser(groupAccountMembers);
    }

    @Override
    public List<GroupAccountMemberResponse> acceptGroupAccountMember(TokenInfo tokenInfo) {
        List<GroupAccountMember> groupAccountMember = groupAccountDao.getGroupAccountMemberFromUserId(
            UUID.fromString(tokenInfo.id())
        );
        return groupAccountMember.stream().map(GroupAccountMemberResponse::from).toList();
    }

    @Override
    public List<GroupAccountMemberResponse> getGroupAccountMember(Account account) {
        List<GroupAccountMember> groupAccountMember = groupAccountDao.getGroupAccountMember(account);

        return groupAccountMember.stream().map(GroupAccountMemberResponse::from).toList();
    }

    @Override
    public void delete(TokenInfo tokenInfo, String reqAccount) {
        if (tokenInfo.id() == null || reqAccount == null || reqAccount.isEmpty()) throw new IllegalArgumentException();
        Account account = accountDao.getAccount(reqAccount);
        if (account.getGroupAccountMembers().size() <= 1) accountDao.deleteAccount(account);
        groupAccountDao.deleteGroupMember(tokenInfo.code(), account);
    }
}
