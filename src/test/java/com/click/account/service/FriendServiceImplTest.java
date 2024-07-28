package com.click.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.response.FriendResponse;
import com.click.account.domain.entity.Friend;
import com.click.account.domain.repository.FriendRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FriendServiceImplTest {

    @Mock
    private FriendRepository friendRepository;

    @Mock
    private ApiService apiService;

    @InjectMocks
    private FriendServiceImpl friendService;

    private TokenInfo tokenInfo;
    private String account;

    @BeforeEach
    public void setUp() {
        friendService = new FriendServiceImpl(
            friendRepository,
            apiService
        );
        tokenInfo = new TokenInfo(
            "bfa974e2-817c-4da3-bd46-d841ffb7b17a",
            "BWTUI2",
            "",
            "박박박",
            6
        );
        account = "111222333";
    }

    @Test
    void 친구_목록_읽기() {
        List<Friend> friends = List.of(
            new Friend(
                account,
                UUID.randomUUID(),
                "BHG1TY",
                "",
                "김김김"
            ),
            new Friend(
                account,
                UUID.randomUUID(),
                "TY4R9I",
                "",
                "이이이"
            )
        );
        Mockito.when(friendRepository.findByAccount(account)).thenReturn(friends);

        // when
        List<FriendResponse> friendResponses = friendService.getFriends(tokenInfo, account);

        // then
        assertEquals(2, friendResponses.size());
        verify(friendRepository, times(1)).findByAccount(account);
    }

    @Test
    void getFriendsNotValidTokenInfo() {
        tokenInfo = null;
        // then
        assertThrows(IllegalArgumentException.class, () -> friendService.getFriends(tokenInfo, account));
    }

    @Test
    void 친구_저장_성공() {
        List<Friend> friends = List.of(
            new Friend(
                account,
                UUID.randomUUID(),
                "BHG1TY",
                "",
                "김김김"
            ),
            new Friend(
                account,
                UUID.randomUUID(),
                "TY4R9I",
                "",
                "이이이"
            )
        );

        when(apiService.getFriendsInfo(tokenInfo.code(), account)).thenReturn(friends);

        // when
        friendService.save(tokenInfo.code(), account);

        // then
        verify(apiService, times(1)).getFriendsInfo(tokenInfo.code(), account);
        verify(friendRepository, times(1)).saveAll(friends);
    }

    @Test
    void saveFriendsListIsEmpty() {
        // given
        List<Friend> friends = List.of();

        when(apiService.getFriendsInfo(tokenInfo.code(), account)).thenReturn(friends);

        // then
        assertThrows(IllegalArgumentException.class, () -> friendService.save(tokenInfo.code(), account));
        verify(apiService, times(1)).getFriendsInfo(tokenInfo.code(), account);
        verify(friendRepository, never()).saveAll(any());
    }
}