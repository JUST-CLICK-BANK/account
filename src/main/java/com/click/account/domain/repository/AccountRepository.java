package com.click.account.domain.repository;

import com.click.account.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccount(String account);
    Optional<Account>  findByUserIdAndAccount (UUID userId, String account);
}
