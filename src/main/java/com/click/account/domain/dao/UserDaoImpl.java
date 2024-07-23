package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUser(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User save(UUID userId, TokenInfo tokenInfo) {
         User user = User.builder()
            .userId(userId)
            .userNickName(tokenInfo.name())
            .userPorfileImg(tokenInfo.img())
            .userCode(tokenInfo.code())
            .rank(tokenInfo.rank())
            .build();
         return userRepository.save(user);
    }

    @Override
    public User saveUser(UUID userId, TokenInfo tokenInfo) {
        return userRepository.save(
            User.builder()
                .userId(userId)
                .userNickName(tokenInfo.name())
                .userPorfileImg(tokenInfo.img())
                .userCode(tokenInfo.code())
                .rank(tokenInfo.rank())
                .build()
        );
    }


}
