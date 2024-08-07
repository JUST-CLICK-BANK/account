package com.click.account.domain.dao;

import com.click.account.config.exception.GroupAccountMemeberException;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.GroupAccountMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GroupAccountDaoImpl implements GroupAccountDao{
    private final GroupAccountMemberRepository groupAccountMemberRepository;
    private final AccountDao accountDao;
    private final UserDao userDao;

    @Override
    public void save(GroupAccountMember groupAccountMember) {
        groupAccountMemberRepository.save(groupAccountMember);
    }

    @Override
    public void saveGroupToUser(User user, Account account) {
        boolean checkAdmin = !groupAccountMemberRepository.existsByAccountAndAdminIsTrue(account);
        log.info("{}", checkAdmin);

        groupAccountMemberRepository.save(
                GroupAccountMember.builder()
                        .account(account)
                        .admin(checkAdmin)
                        .status(true)
                        .inviteCode(account.getGroupAccountCode())
                        .user(user)
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
    public List<GroupAccountMember> getGroupAccountMemberFromUser(User user) {
        List<GroupAccountMember> groupAccountMembers = groupAccountMemberRepository.findByUserAndStatus(user, false);
        if (groupAccountMembers.isEmpty()) throw new GroupAccountMemeberException();
        return groupAccountMembers;
    }

    @Override
    public GroupAccountMember getGroupAccountMemberFromStatusIsTrue(String inviteCode, Account account) {
        return groupAccountMemberRepository.findByInviteCodeAndAccountAndStatusIsTrue(inviteCode, account)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public GroupAccountMember getGroupAccountMemberStatusIsFalse(User user, Account account) {
        return groupAccountMemberRepository.findByUserAndAccount(user, account)
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
