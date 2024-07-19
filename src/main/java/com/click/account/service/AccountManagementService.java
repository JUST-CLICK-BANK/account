package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.AccountRequest;
import java.util.UUID;

public interface AccountManagementService {
    void saveAccount(TokenInfo tokenInfo, AccountRequest req);
    void saveGroupAccount(TokenInfo tokenInfo, AccountRequest req);
}
