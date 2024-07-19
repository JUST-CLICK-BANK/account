package com.click.account.domain.repository;

import com.click.account.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByAccount(String account);
//    Optional<Account>  findOptionalByUserIdAndAccount (UUID userId, String account);
//    findOptionalByUserIdAndAccount
//    List<Account> findByUserId(UUID userId);
//    List<Account> findByUserIdAndAccountDisable(UUID userId, Boolean accountDisable);

//    findByUserIdAndAccountAndAccountDisable
   List<Account> findByUser_UserIdAndAccountDisable(UUID userId, Boolean accountDisable);
    Optional<Account> findOptionalByUser_UserIdAndAccount(UUID userId, String account);

    Optional<Account> findByUser_UserIdAndAccountAndAccountDisable(UUID userId, String account,
        Boolean accountDisable);
}

