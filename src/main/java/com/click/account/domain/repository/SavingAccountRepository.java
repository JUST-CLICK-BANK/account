package com.click.account.domain.repository;

import com.click.account.domain.entity.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {

}
