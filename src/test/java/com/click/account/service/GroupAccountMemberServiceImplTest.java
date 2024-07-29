package com.click.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.group.GroupAccountMemberRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Friend;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.repository.FriendRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GroupAccountMemberServiceImplTest {
    @Mock
    private FriendRepository friendRepository;

    @Mock
    private GroupAccountDao groupAccountDao;

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private GroupAccountMemberServiceImpl groupAccountMemberService;

    private TokenInfo tokenInfo;

    @BeforeEach
    public void setUpTest() {
        groupAccountMemberService = new GroupAccountMemberServiceImpl(
            friendRepository,
            groupAccountDao,
            accountDao
        );
        tokenInfo = new TokenInfo(
            "bfa974e2-817c-4da3-bd46-d841ffb7b17a",
            "BWTUI2",
            "",
            "박박박",
            6
        );
    }

    @Test
    void 모임_통장_유저_저장_성공() {
        // given
        Friend friend = new Friend();

        when(friendRepository.findById(UUID.fromString(tokenInfo.id()))).thenReturn(Optional.of(friend));

        // when
        groupAccountMemberService.save(tokenInfo);

        // then
        verify(friendRepository, times(1)).findById(UUID.fromString(tokenInfo.id()));
        verify(groupAccountDao, times(1)).saveGroupToUser(tokenInfo, friend.getAccount(), UUID.fromString(tokenInfo.id()));
    }

    @Test
    void friendNotFound() {
        // given
        when(friendRepository.findById(UUID.fromString(tokenInfo.id()))).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> groupAccountMemberService.save(tokenInfo));
    }

    @Test
    void 모임_통장_요청_수락_대기_저장_성공() {
        // given
        String reqAccount = "111222333";
        List<GroupAccountMemberRequest> requests = List.of(
            new GroupAccountMemberRequest(
                UUID.randomUUID(),
                "QWTOIU",
                "",
                "김김김"
            )
        );

        Account account = new Account();
        List<GroupAccountMember> groupAccountMembers = List.of(
            new GroupAccountMember(
                1L,
                requests.get(0).img(),
                requests.get(0).name(),
                false,
                requests.get(0).code(),
                false,
                requests.get(0).id(),
                account
            )
        );

        when(accountDao.getAccount(reqAccount)).thenReturn(account);

        // when
        groupAccountMemberService.saveWaitingMember(tokenInfo, reqAccount, requests);

        // then
        ArgumentCaptor<List<GroupAccountMember>> captor = ArgumentCaptor.forClass(List.class);
        verify(accountDao, times(1)).getAccount(reqAccount);
        verify(groupAccountDao, times(1)).waitGroupAccountUser(captor.capture());

        List<GroupAccountMember> capturedMembers = captor.getValue();
        assertEquals(1, capturedMembers.size());
        assertEquals(requests.get(0).id(), capturedMembers.get(0).getUserId());
        assertEquals(requests.get(0).code(), capturedMembers.get(0).getUserCode());
        assertEquals(requests.get(0).name(), capturedMembers.get(0).getUserNickName());
        assertEquals(account, capturedMembers.get(0).getAccount());
    }

    @Test
    void 유저_모임_통장_대기_목록() {
        // given
        List<GroupAccountMember> groupAccountMembers = List.of(new GroupAccountMember());

        when(groupAccountDao.getGroupAccountMemberFromUserId(UUID.fromString(tokenInfo.id()))).thenReturn(groupAccountMembers);

        // when
        List<GroupAccountMemberResponse> responses = groupAccountMemberService.acceptGroupAccountMember(tokenInfo);

        // then
        assertEquals(1, responses.size());
        verify(groupAccountDao, times(1)).getGroupAccountMemberFromUserId(UUID.fromString(tokenInfo.id()));
    }

    @Test
    void 모임_통장_공유_멤버() {
        // given
        Account account = new Account();
        List<GroupAccountMember> groupAccountMembers = List.of(new GroupAccountMember());

        when(groupAccountDao.getGroupAccountMember(account)).thenReturn(groupAccountMembers);

        // when
        List<GroupAccountMemberResponse> responses = groupAccountMemberService.getGroupAccountMember(account);

        // then
        assertEquals(1, responses.size());
        verify(groupAccountDao, times(1)).getGroupAccountMember(account);
    }

    @Test
    void 모임_탈퇴_성공() {
        // given
        String reqAccount = "111222333";
        List<GroupAccountMember> members = List.of(new GroupAccountMember());
        Account account = Account.builder()
            .groupAccountMembers(members)
            .build();
        List<GroupAccountMember> groupAccountMember = List.of(
            new GroupAccountMember(
                null,
                tokenInfo.img(),
                tokenInfo.name(),
                false,
                tokenInfo.code(),
                true,
                UUID.fromString(tokenInfo.id()),
                account
            ),
            new GroupAccountMember(
                null,
                tokenInfo.img(),
                tokenInfo.name(),
                false,
                tokenInfo.code(),
                true,
                UUID.fromString(tokenInfo.id()),
                account
            )
        );

        when(accountDao.getAccount(reqAccount)).thenReturn(account);
        when(groupAccountDao.getGroupAccountStatusIsTrue(account)).thenReturn(1L);
        when(groupAccountDao.getGroupAccountMemberFromStatusIsTrue(tokenInfo.code(), account)).thenReturn(groupAccountMember);

        // when
        groupAccountMemberService.delete(tokenInfo, reqAccount);

        // then
        verify(accountDao, times(1)).getAccount(reqAccount);
        verify(groupAccountDao, times(1)).deleteGroupMember(groupAccountMember);
    }

    @Test
    void groupMemberNotFoundTokenAndRequestAccount() {
        // given
        TokenInfo tokenInfo = new TokenInfo(
            null,
            "BWTUI2",
            "",
            "박박박",
            6
        );
        String reqAccount = "";

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupAccountMemberService.delete(tokenInfo, reqAccount));
    }

    @Test
    void deleteAccountWhenSingleMember() {
        // given
        String reqAccount = "111222333";
        List<GroupAccountMember> members = List.of(new GroupAccountMember());
        Account account = Account.builder()
            .groupAccountMembers(members)
            .build();
        GroupAccountMember groupAccountMember = new GroupAccountMember(
            null,
            tokenInfo.img(),
            tokenInfo.name(),
            false,
            tokenInfo.code(),
            false,
            UUID.fromString(tokenInfo.id()),
            account
        );

        when(accountDao.getAccount(reqAccount)).thenReturn(account);
        when(groupAccountDao.getGroupAccountStatusIsTrue(account)).thenReturn(1L);
        when(groupAccountDao.getGroupAccountMemberFromStatusIsTrue(tokenInfo.code(), account)).thenReturn(groupAccountMember);

        // when
        groupAccountMemberService.delete(tokenInfo, reqAccount);

        // then
        verify(accountDao, times(1)).deleteAccount(account);
        verify(groupAccountDao, times(1)).deleteGroupMember(groupAccountMember);
    }
}