package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;

import java.util.UUID;

public interface GroupAccountDao {
    void saveGroupToUser(TokenInfo tokenInfo, String Account, UUID userId);
}
