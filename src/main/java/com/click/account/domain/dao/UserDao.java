package com.click.account.domain.dao;

import com.click.account.domain.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    Optional<User> getUser(UUID userId);
    User getUserFromUserCode(String userCode);
    User save(User user);
}
