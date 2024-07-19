package com.click.account.service;

import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.dto.response.AccountResponse;
import com.click.account.domain.dto.response.GroupAccountResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccount;
import com.click.account.domain.repository.AccountRepository;
import com.click.account.domain.repository.GroupAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final GroupAccountDao groupAccountDao;
    private final AccountRepository accountRepository;
    private final GroupAccountRepository groupAccountRepository;

    @Override
    public void saveAccount(TokenInfo tokenInfo, AccountRequest req) {

        UUID userId = UUID.fromString(tokenInfo.id());

        // 중복된 계좌가 있는지 확인 후 새로운 계좌 생성
        String account =  makeAccount();
        // 일반 계좌 생성
        if (req.status().equals("account"))
            accountDao.saveAccount(req, account, userId);

        // 모임 통장 계좌 생성
        if (req.status().equals("group")) {
            accountDao.saveGroupAccount(req, account, userId);
            groupAccountDao.saveGroupToUser(tokenInfo, account, userId);
        }
    }

    private String makeAccount() {
        String account = GenerateAccount.generateAccount();
        if(accountDao.compareAccount(account)) return makeAccount();
        return account;
    }

    @Override
    public void updateName(UUID userId, AccountNameRequest req) {
        accountDao.updateName(userId, req);
    }

    @Override
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        accountDao.updatePassword(userId, req);
    }

    @Override
    public void updateMoney(UUID userId, AccountMoneyRequest req) {
        if (req.status().equals("deposit")) {
            Long money = accountDao.getAccount(userId, req.account()).getMoneyAmount() + req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
        }
        // 돈이 0원일때 예외 처리 필요
        if (req.status().equals("transfer")) {
            Long money = accountDao.getAccount(userId, req.account()).getMoneyAmount() - req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
        }
    }

    @Override
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        accountDao.updateAccountLimit(userId, req);
    }

    @Override
    public List<UserAccountResponse> findUserAccountByUserIdAndAccount(UUID userId,TokenInfo tokenInfo) {
        List<Account> disabledAccount = accountRepository.findByUserIdAndAccountDisable(userId,true);
        if (disabledAccount.isEmpty()) {
            throw new IllegalArgumentException("게좌 없음");
        }
        List<AccountResponse> accountResponses = disabledAccount.stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());

        return List.of(UserAccountResponse.from(accountResponses, tokenInfo));
    }
//
//    @Override
//    public List<GroupAccountResponse> findUserAccountByUserIdAndAccount(UUID userId, String nickName, String profileImg) {
//        List<Account> disableaccount = accountRepository.findByUserIdAndAccountDisable(userId,true);
//        if (disableaccount.isEmpty()){
//            throw new IllegalArgumentException("계좌 없음");
//        }
//        return disableaccount.stream()
//                .map(account -> {
//                    GroupAccount groupAccount = groupAccountRepository.findByAccountAndUserId(account.getAccount(), userId).orElseThrow(IllegalArgumentException::new);
//                    return GroupAccountResponse.from(account, groupAccount);
//                })
//                .collect(Collectors.toList());
//    }


    @Override
    public String findGroupAccountCodeByUserIdAndAccount(UUID userId, String account) {
        Account accountResult = accountRepository.findByUserIdAndAccountAndAccountDisable(userId, account,true);
        if (accountResult == null) {
            throw new IllegalArgumentException("그룹 코드를 찾을 수 없습니다.");
        }
        return accountResult.getGroupAccountCode(); // 바로 GroupAccountCode 반환

    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findOptionalByUserIdAndAccount(userId, account).orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
    }
}

