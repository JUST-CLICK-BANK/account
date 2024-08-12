package com.click.account.domain.repository;

import com.click.account.domain.entity.Transfer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<Transfer> findByMyAccount(String account);
}
