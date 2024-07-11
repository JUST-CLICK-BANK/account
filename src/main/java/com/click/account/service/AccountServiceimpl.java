package com.click.account.service;

import com.click.account.config.constants.TransferLimit;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.dto.request.GroupAccountRequest;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceimpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public void saveAccount(UUID userId, AccountRequest req) {
        if (req == null) throw new IllegalArgumentException();
        accountRepository.save(req.toEntity(userId, TransferLimit.getDailyLimit(), TransferLimit.getOnetimeLimit()));
    }

    @Override
    public void saveGroupAccount(UUID userId, GroupAccountRequest req) {
        if (req == null) throw new IllegalArgumentException();
        accountRepository.save(req.toEntity(userId, TransferLimit.getDailyLimit(), TransferLimit.getOnetimeLimit(), ))
    }
}
