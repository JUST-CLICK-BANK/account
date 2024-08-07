package com.click.account.domain.dao;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import java.util.List;

public interface GroupAccountDao {
    void save(GroupAccountMember groupAccountMember);
    void saveGroupToUser(User user, Account account);
    void waitGroupAccountUser(List<GroupAccountMember> groupAccountMembers);
    List<GroupAccountMember> getGroupAccountMember(Account account);
    List<GroupAccountMember> getGroupAccountMemberFromUser(User user);
    GroupAccountMember getGroupAccountMemberFromStatusIsTrue(String groupCode, Account account);
    GroupAccountMember getGroupAccountMemberStatusIsFalse(User user, Account account);
    long getGroupAccountStatusIsTrue(Account account);
    void deleteGroupMember(GroupAccountMember groupAccountMember);
}
