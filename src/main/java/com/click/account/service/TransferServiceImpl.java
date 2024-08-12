package com.click.account.service;

import com.click.account.config.constants.TransferType;
import com.click.account.domain.dto.request.account.TransferRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Transfer;
import com.click.account.domain.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{
    private final TransferRepository transferRepository;

    @Override
    public void save(TransferRequest req, String myAccount) {
        if (req == null) throw new IllegalArgumentException("Not Found Request");
        Integer type = TransferType.SAVING.getTransferType();
        log.info(String.valueOf(req.transferDate()));
        transferRepository.save(req.toEntity(type, myAccount));
    }
}
