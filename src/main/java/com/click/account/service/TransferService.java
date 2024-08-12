package com.click.account.service;

import com.click.account.domain.dto.request.account.TransferRequest;
import com.click.account.domain.entity.Account;

public interface TransferService {
    void save(TransferRequest req, String myAccount);
}
