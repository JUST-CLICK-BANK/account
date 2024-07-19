package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.entity.GroupAccount;
import com.click.account.domain.repository.GroupAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GroupAccountDaoTest {

    @Mock
    private GroupAccountRepository groupAccountRepository;

    @InjectMocks
    private GroupAccountDaoImpl groupAccountDao;

    @Test
    void 모임_통장_생성시_유저_정보_저장_성공() {
        // given
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        TokenInfo tokenInfo = new TokenInfo(
                "71a90366-30e6-4e7e-a259-01a7947ff866",
                "HSJF2S",
                "",
                "박박박",
                null
        );

        // when
        groupAccountDao.saveGroupToUser(tokenInfo, account, userId);

        // then
        Mockito.verify(groupAccountRepository).save(any(GroupAccount.class));
    }

    @Test
    void 모임_통장_생성시_유저_정보_저장_실패() {
        // given
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        TokenInfo tokenInfo = new TokenInfo(
                "71a90366-30e6-4e7e-a259-01a7947ff866",
                "HSJF2S",
                "",
                "박박박",
                null
        );

        Mockito.doThrow(new RuntimeException("Database error"))
                .when(groupAccountRepository).save(any(GroupAccount.class));

        // when, then
        RuntimeException thrown = Assertions
                .assertThrows(
                        RuntimeException.class, () -> {
                            groupAccountDao.saveGroupToUser(tokenInfo, account, userId);
                        }
                );
        Assertions.assertEquals("Database error", thrown.getMessage());
    }
}