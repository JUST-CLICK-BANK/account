package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.group.GroupAccountMemberRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.repository.AccountRepository;
import com.click.account.domain.repository.GroupAccountMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GroupAccountDaoImpl implements GroupAccountDao{
    private final GroupAccountMemberRepository groupAccountMemberRepository;
    private final AccountRepository accountRepository;

    @Override
    public void saveGroupToUser(TokenInfo tokenInfo, String account, UUID userId) {
        Account getAccount = accountRepository.findByAccount(account).orElseThrow(IllegalArgumentException::new);
        boolean checkAdmin = !groupAccountMemberRepository.existsByAccountAndAdminIsTrue(getAccount);
        log.info("{}", checkAdmin);

        groupAccountMemberRepository.save(
                GroupAccountMember.builder()
                        .account(getAccount)
                        .userCode(tokenInfo.code())
                        .userNickName(tokenInfo.name())
                        .userPofileImg(tokenInfo.img())
                        .admin(checkAdmin)
                        .status(true)
                        .build()
        );
    }

    @Override
    public void waitGroupAccountUser(List<GroupAccountMember> groupAccountMembers) {
        groupAccountMemberRepository.saveAll(groupAccountMembers);
    }

    @Override
    public List<GroupAccountMember> getGroupAccountMember(Account account) {
        return groupAccountMemberRepository.findByAccountAndStatusIsTrue(account);
    }

    @Override
    public List<GroupAccountMember> getGroupAccountMemberFromUserId(UUID userId) {
        List<GroupAccountMember> groupAccountMembers = groupAccountMemberRepository.findByUserIdAndStatus(userId, false);
        if (groupAccountMembers.isEmpty()) throw new IllegalArgumentException("요청을 진행할 친구가 없습니다.");
        return groupAccountMembers;
    }

    @Override
    public void deleteGroupMember(String userCode, Account account) {
        GroupAccountMember groupAccountMember = groupAccountMemberRepository.findByUserCodeAndAccount(userCode, account)
            .orElseThrow(IllegalArgumentException::new);
        groupAccountMemberRepository.delete(groupAccountMember);
    }
}
