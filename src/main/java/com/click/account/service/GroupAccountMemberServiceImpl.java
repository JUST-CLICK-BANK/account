package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dao.UserDao;
import com.click.account.domain.dto.request.group.GroupAccountMemberRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Friend;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.FriendRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupAccountMemberServiceImpl implements GroupAccountMemberService {
    private final FriendRepository friendRepository;
    private final GroupAccountDao groupAccountDao;
    private final AccountDao accountDao;
    private final UserService userService;
    private final UserDao userDao;

    // 친구 요청 승인 시 모임 통장에 저장 (status = true)
    @Override
    public void save(TokenInfo tokenInfo, Boolean status) {
        Friend friend = friendRepository.findById(UUID.fromString(tokenInfo.id()))
            .orElseThrow(IllegalArgumentException::new);
        Account account = accountDao.getAccount(friend.getAccount());
        User user = userService.getUser(tokenInfo);
        GroupAccountMember groupAccountMember =
            groupAccountDao.getGroupAccountMemberStatusIsFalse(user, account);

        if (!status) {
            groupAccountDao.deleteGroupMember(groupAccountMember);
        } else {
            groupAccountMember.setStatus(true);
            groupAccountDao.save(groupAccountMember);
        }
    }

    // 친구 요청 시 임시적으로 저장 (status = false)
    @Override
    public void saveWaitingMember(TokenInfo tokenInfo, String reqAccount, List<GroupAccountMemberRequest> requests) {
        Account account = accountDao.getAccount(reqAccount);

        List<User> users = requests.stream().map(request -> User
            .builder()
            .userId(UUID.fromString(request.id()))
            .userCode(request.code())
            .userPorfileImg(request.img())
            .userNickName(request.name())
            .rank(request.rank())
            .build()
        ).toList();

        for (User user: users) {
            Friend friend = friendRepository.findByAccountAndFriendId(account.getAccount(), user.getUserId());
            if (friend == null)
                throw new IllegalArgumentException("친구가 없습니다.");
            userDao.save(user);
        }

        List<GroupAccountMember> groupAccountMembers = GroupAccountMemberRequest.toEntities(requests, account, users);

        groupAccountDao.waitGroupAccountUser(groupAccountMembers);
    }

    // 모임 통장 수락 및 거절 요청 목록 읽기
    @Override
    public List<GroupAccountMemberResponse> acceptGroupAccountMember(TokenInfo tokenInfo) {
        User user = userService.getUser(tokenInfo);
        List<GroupAccountMember> groupAccountMembers = groupAccountDao.getGroupAccountMemberFromUser(user);

        return groupAccountMembers.stream()
            .map(groupAccountMember -> userDao.getUserFromUserCode(groupAccountMember.getUser().getUserCode()))
            .filter(Objects::nonNull)
            .map(GroupAccountMemberResponse::from)
            .toList();
    }

    @Override
    public void delete(TokenInfo tokenInfo, String reqAccount) {
        if (tokenInfo.id() == null || reqAccount == null || reqAccount.isEmpty()) throw new IllegalArgumentException();
        User user = userService.getUser(tokenInfo);
        Account account = accountDao.getAccount(reqAccount);

        GroupAccountMember groupAccountMember = groupAccountDao.getGroupAccountMemberStatusIsFalse(user, account);
      
        if (groupAccountDao.getGroupAccountStatusIsTrue(account) <= 1) accountDao.deleteAccount(account);
        groupAccountDao.deleteGroupMember(groupAccountMember);
    }
}
