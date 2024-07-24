package com.click.account.domain.repository;

import com.click.account.domain.dto.request.GroupAccountRequest;
import com.click.account.domain.entity.GroupAccountMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaAuditing
class GroupAccountRepositoryTest {
    @Autowired
    private GroupAccountRepository groupAccountRepository;

    @Test
    void 모임_통장_게좌_생성() {
        String account = "111222333333";
        Long friendId = 1L;
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String nickName = "박박박";
        String img = "asdasd";

        GroupAccountRequest request = new GroupAccountRequest(
                userId,
                nickName,
                img
        );

        GroupAccountMember groupAccount = groupAccountRepository.save(request.toEntity(account));

        Assertions.assertEquals(groupAccount.getAccount(), account);
        Assertions.assertEquals(groupAccount.getFriendId(), friendId);
    }

    @Test
    void 모임_통장_게좌_생성_유저_인증_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String nickName = "박박박";
        String img = "asdasd";

        GroupAccountRequest request = new GroupAccountRequest(
                userId,
                nickName,
                img
        );

        GroupAccountMember groupAccount = groupAccountRepository.save(request.toEntity(account));

        Assertions.assertNotEquals(groupAccount.getAccount(), "111222333334");
        Assertions.assertNotEquals(groupAccount.getUserId(), UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff865"));
    }

    @Test
    void 계좌_유저_찾기_성공() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String nickName = "박박박";
        String img = "asdasd";

        GroupAccountRequest request = new GroupAccountRequest(
                userId,
                nickName,
                img
        );

        GroupAccountMember groupAccount = groupAccountRepository.save(request.toEntity(account));

        Optional<GroupAccountMember> getGroupAccount = groupAccountRepository.findByAccountAndUserId(groupAccount.getAccount(), groupAccount.getUserId());

        Assertions.assertTrue(getGroupAccount.isPresent());
        Assertions.assertEquals(getGroupAccount.get().getAccount(), groupAccount.getAccount());
        Assertions.assertEquals(getGroupAccount.get().getUserId(), groupAccount.getUserId());
    }

    @Test
    void 계좌_유저_찾기_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String nickName = "박박박";
        String img = "asdasd";

        GroupAccountRequest request = new GroupAccountRequest(
                userId,
                nickName,
                img
        );

        groupAccountRepository.save(request.toEntity(account));

        Optional<GroupAccountMember> getGroupAccount = groupAccountRepository.findByAccountAndUserId("111222333334", UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff865"));

        Assertions.assertFalse(getGroupAccount.isPresent());
    }

    @Test
    void 모임_통장_관리자_찾기_성공() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String nickName = "박박박";
        String img = "asdasd";

        GroupAccountRequest request = new GroupAccountRequest(
                userId,
                nickName,
                img
        );

        GroupAccountMember groupAccount = groupAccountRepository.save(request.toEntity(account));

        boolean checkAdmin = groupAccountRepository.existsByAccountAndAdminIsTrue(groupAccount.getAccount());

        Assertions.assertTrue(checkAdmin);
    }

    @Test
    void 모임_통장_관리자_찾기_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String nickName = "박박박";
        String img = "asdasd";

        GroupAccountRequest request = new GroupAccountRequest(
                userId,
                nickName,
                img
        );

        groupAccountRepository.save(request.toEntity(account));

        boolean checkAdmin = groupAccountRepository.existsByAccountAndAdminIsTrue("111222333334");

        Assertions.assertFalse(checkAdmin);
    }

}