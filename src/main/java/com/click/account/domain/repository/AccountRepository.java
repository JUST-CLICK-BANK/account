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

    @Query("SELECT distinct a FROM Account a JOIN a.user u LEFT JOIN a.groupAccountMembers gam WHERE (u.userId = :userId OR gam.user.userId = :userId) AND a.accountAble = :accountAble")
    List<Account> findAccounts(@Param("userId") UUID userId, @Param("accountAble") Boolean accountAble);

    @Query("SELECT a FROM Account a JOIN FETCH a.user u WHERE u.userId = :userId AND a.account = :account")
    Optional<Account> findUserIdAndAccount(@Param("userId") UUID userId, @Param("account") String account);

    @Query("SELECT a FROM Account a WHERE a.user.userId = :userId AND a.type = :type")
    List<Account> findAccountToType(@Param("userId") UUID userId, @Param("type") Integer type);
}

