package com.click.account.domain.dao;

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
    public User getUserFromUserCode(String userCode) {
        return userRepository.findByUserCode(userCode).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public User save(User user) {
         return userRepository.save(user);
    }


}
