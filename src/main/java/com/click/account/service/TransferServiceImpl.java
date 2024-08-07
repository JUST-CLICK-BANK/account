package com.click.account.service;

import com.click.account.config.constants.TransferType;
import com.click.account.domain.dto.request.account.TransferRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Transfer;
import com.click.account.domain.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{
    private final TransferRepository transferRepository;


    @Override
    public void save(Account account, TransferRequest req) {
        if (req == null) throw new IllegalArgumentException("Not Found Request");
        int type = TransferType.SAVING.getTransferType();
        Transfer transfer = Transfer.builder()
            .account(account)
            .type(type)
            .amount(req.amount())
            .transferDate(req.transferDate())
            .account(account)
            .build();

        transferRepository.save(transfer);
    }
}
