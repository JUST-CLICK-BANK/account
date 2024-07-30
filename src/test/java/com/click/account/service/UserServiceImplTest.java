package com.click.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.UserDao;
import com.click.account.domain.dto.response.UserResponse;
import com.click.account.domain.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private TokenInfo tokenInfo;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(
            userDao
        );
        tokenInfo = new TokenInfo(
            "bfa974e2-817c-4da3-bd46-d841ffb7b17a",
            "BWTUI2",
            "",
            "박박박",
            6
        );
        userId = UUID.fromString(tokenInfo.id());
    }

    @Test
    void 유저_정보_읽기() {
        // given
        User user = User.builder().userCode(tokenInfo.code()).build();
        when(userDao.getUser(userId)).thenReturn(Optional.of(user));

        // when
        User result = userService.getUser(tokenInfo);

        // then
        verify(userDao, times(1)).getUser(userId);
        verify(userDao, never()).save(any(UUID.class), any(TokenInfo.class));
        assertEquals(user, result);
    }

    @Test
    void 등록된_유저_없을시_정보_저장_성공() {
        // given
        User newUser = User.builder().userCode(tokenInfo.code()).build();
        when(userDao.getUser(userId)).thenReturn(Optional.empty());
        when(userDao.save(userId, tokenInfo)).thenReturn(newUser);

        // when
        User result = userService.getUser(tokenInfo);

        // then
        verify(userDao, times(1)).getUser(userId);
        verify(userDao, times(1)).save(userId, tokenInfo);
        assertEquals(newUser, result);
    }

    @Test
    void 계좌_없을때_유저_정보만_호출() {
        // when
        UserResponse result = userService.getUserInfo(tokenInfo);

        // then
        assertEquals(tokenInfo.name(), result.userName());
        assertEquals(tokenInfo.img(), result.userImg());
        assertEquals(tokenInfo.code(), result.userCode());
    }

}