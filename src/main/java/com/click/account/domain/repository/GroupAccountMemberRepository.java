package com.click.account.domain.repository;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface GroupAccountMemberRepository extends JpaRepository<GroupAccountMember, Long> {
    boolean existsByAccountAndAdminIsTrue(Account account);
    List<GroupAccountMember> findByAccountAndStatusIsTrue(Account account);
    Optional<GroupAccountMember> findByUserCodeAndAccountAndStatusIsTrue(String userCode, Account account);
    List<GroupAccountMember> findByUserIdAndStatus(UUID userId, Boolean status);
}
