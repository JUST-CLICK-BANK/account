package com.click.account.domain.repository;

import com.click.account.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccount(String account);
    Optional<Account>  findOptionalByUserIdAndAccount (UUID userId, String account);
    List<Account> findByUserId(UUID userId);
    List<Account> findByUserIdAndAccountDisable(UUID userId, Boolean accountDisable);
    List<Account> findByUserIdAndAccountAndAccountDisable(UUID userId, String account, Boolean accountDisable);
}
