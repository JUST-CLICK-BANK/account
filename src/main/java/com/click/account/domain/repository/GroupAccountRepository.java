package com.click.account.domain.repository;

import com.click.account.domain.entity.GroupAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupAccountRepository extends JpaRepository<GroupAccount, Long> {
    Optional<GroupAccount> findByAccountAndUserId(String account, UUID userId);
    boolean existsByAccountAndAdminIsTrue(String account);
}
