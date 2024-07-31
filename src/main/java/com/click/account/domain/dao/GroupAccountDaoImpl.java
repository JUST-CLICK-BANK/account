package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
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
    private final AccountDao accountDao;

    @Override
    public void save(GroupAccountMember groupAccountMember) {
        groupAccountMemberRepository.save(groupAccountMember);
    }

    @Override
    public void saveGroupToUser(TokenInfo tokenInfo, String reqAccount) {
        Account account = accountDao.getAccount(reqAccount);
        boolean checkAdmin = !groupAccountMemberRepository.existsByAccountAndAdminIsTrue(account);

        log.info("{}", checkAdmin);

        groupAccountMemberRepository.save(
                GroupAccountMember.builder()
                        .account(account)
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
    public GroupAccountMember getGroupAccountMemberFromStatusIsTrue(String userCode, Account account) {
        return groupAccountMemberRepository.findByUserCodeAndAccountAndStatusIsTrue(userCode, account)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public GroupAccountMember getGroupAccountMemberStatusIsFalse(String userCode, Account account) {
        return groupAccountMemberRepository.findByUserCodeAndAccount(userCode, account)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public long getGroupAccountStatusIsTrue(Account account) {
        return groupAccountMemberRepository.findByAccountAndStatusIsTrue(account)
            .stream().filter(GroupAccountMember::isStatus).count();
    }

    @Override
    public void deleteGroupMember(GroupAccountMember groupAccountMember) {
        groupAccountMemberRepository.delete(groupAccountMember);
    }
}
