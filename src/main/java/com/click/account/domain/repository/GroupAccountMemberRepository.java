package com.click.account.domain.repository;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupAccountMemberRepository extends JpaRepository<GroupAccountMember, Long> {
    boolean existsByAccountAndAdminIsTrue(Account account);
    List<GroupAccountMember> findByAccountAndStatusIsTrue(Account account);
    Optional<GroupAccountMember> findByInviteCodeAndAccountAndStatusIsTrue(String inviteCode, Account account);
    List<GroupAccountMember> findByUserAndStatus(User user, Boolean status);
    Optional<GroupAccountMember> findByUserAndAccount(User user, Account account);
}
