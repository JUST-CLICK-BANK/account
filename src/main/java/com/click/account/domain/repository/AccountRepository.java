package com.click.account.domain.repository;

import com.click.account.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByAccount(String account);

    @Query("SELECT a FROM Account a JOIN FETCH a.user u WHERE u.userId = :userId AND a.accountDisable = :accountDisable")
    List<Account> findAccounts(@Param("userId") UUID userId, @Param("accountDisable") Boolean accountDisable);

    @Query("SELECT a FROM Account a JOIN FETCH a.user u WHERE u.userId = :userId AND a.account = :account")
    Optional<Account> findUserIdAndAccount(@Param("userId") UUID userId, @Param("account") String account);
}

