package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.UserDao;
import com.click.account.domain.dto.response.UserResponse;
import com.click.account.domain.entity.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User getUser(UUID userId, TokenInfo tokenInfo) {
        User user;
        if(userDao.getUser(userId).isPresent()) {
            user = userDao.getUser(userId).get();
        } else user = userDao.save(userId, tokenInfo);
        System.out.println(user.getUserCode());
        return user;
    }

    @Override
    public UserResponse getUserInfo(TokenInfo tokenInfo) {
        return UserResponse.from(tokenInfo.name(), tokenInfo.img(), tokenInfo.code());
    }
}
