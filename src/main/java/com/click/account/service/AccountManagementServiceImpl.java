package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManagementServiceImpl implements AccountManagementService{

    private final AccountDao accountDao;
    private final GroupAccountDao groupAccountDao;

    @Override
    public void saveAccount(TokenInfo tokenInfo, AccountRequest req) {

    }

    @Override
    public void saveGroupAccount(TokenInfo tokenInfo, AccountRequest req) {

    }
}
