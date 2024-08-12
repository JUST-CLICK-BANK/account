package com.click.account.service;

import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dto.request.account.SavingRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.SavingAccount;
import com.click.account.domain.entity.Transfer;
import com.click.account.domain.repository.SavingAccountRepository;
import com.click.account.domain.repository.TransferRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService{
    private final SavingAccountRepository savingAccountRepository;
    private final TransferRepository transferRepository;
    private final AccountDao accountDao;

    @Override
    public void save(SavingRequest req, String account) {
        LocalDate endAt = LocalDate.now().plusYears(req.term());
        savingAccountRepository.save(req.toEntity(account, endAt));
    }

    @Override
    @Transactional
    public void delete(Account reqAccount) {
        SavingAccount savingAccount = savingAccountRepository.findByMyAccount(
                reqAccount.getAccount())
            .orElseThrow(IllegalArgumentException::new);
        Account account = accountDao.getAccount(savingAccount.getMyAccount());
        if (account.getMoneyAmount() != 0)
            throw new IllegalArgumentException("돈이 있습니다.");

        Transfer transfer = transferRepository.findByMyAccount(savingAccount.getMyAccount())
            .orElseThrow(IllegalArgumentException::new);
        log.info(transfer.toString());
        savingAccountRepository.delete(savingAccount);
        transferRepository.delete(transfer);
    }
}
