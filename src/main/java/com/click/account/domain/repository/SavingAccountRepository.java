package com.click.account.domain.repository;

import com.click.account.domain.entity.SavingAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {
    Optional<SavingAccount> findByMyAccount(String account);
}
