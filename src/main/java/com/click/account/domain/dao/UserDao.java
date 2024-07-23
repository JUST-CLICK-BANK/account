package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    Optional<User> getUser(UUID userId);
    User save(UUID userId, TokenInfo tokenInfo);
    User saveUser(UUID userId, TokenInfo tokenInfo);

}
