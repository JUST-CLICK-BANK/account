package com.click.account.service;

import com.click.account.domain.dto.request.account.SavingRequest;
import com.click.account.domain.repository.SavingAccountRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService{
    private final SavingAccountRepository savingAccountRepository;

    @Override
    public void save(SavingRequest req, String account) {
        LocalDate endAt = LocalDate.now().plusYears(req.term());
        savingAccountRepository.save(req.toEntity(account, endAt));
    }
}
