package com.click.account.service;

import com.click.account.config.constants.AccountType;
import com.click.account.config.exception.InsufficientAmountException;
import com.click.account.config.exception.LimitTransferException;
import com.click.account.config.exception.NotExistAccountException;
import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.account.GroupCode;
import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.dto.request.account.AccountNameRequest;
import com.click.account.domain.dto.request.account.AccountPasswordRequest;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.dto.request.account.AccountTransferLimitRequest;
import com.click.account.domain.dto.response.AccountAmountResponse;
import com.click.account.domain.dto.response.AccountDetailResponse;
import com.click.account.domain.dto.response.AccountInfoResponse;
import com.click.account.domain.dto.response.AccountResponse;
import com.click.account.domain.dto.response.AutoTransferAccountResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.domain.dto.response.UserResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final GroupAccountDao groupAccountDao;
    private final AccountRepository accountRepository;
    private final ApiService apiService;
    private final UserService userService;
    private final TransferService transferService;
    private final SavingAccountService savingAccountService;
    private final JwtUtils jwtUtils;

    @Override
    public String saveAccount(TokenInfo tokenInfo, AccountRequest req) {
        User user = userService.getUser(tokenInfo);
        Integer type = AccountType.fromString(req.accountStatus());

        // 중복된 계좌가 있는지 확인 후 새로운 계좌 생성
        String makeAccount = makeAccount();

        // 일반 계좌 생성
        if (type == 1) {
            Account account = req.toEntity(
                makeAccount,
                user.getUserNickName()+"의 통장",
                user,
                true,
                type
            );
            accountDao.saveAccount(account);
            return account.getAccount();
        }
        // 모임 통장 계좌 생성
        else if (type == 2) {
            Account account = req.toGroupEntity(
                makeAccount,
                user.getUserNickName()+"의 모임 통장",
                user,
                user.getUserCode(),
                true,
                type
            );
            accountDao.saveAccount(account);
            groupAccountDao.saveGroupToUser(user, account);
       }
        // 적금 계좌 생성
        else if (type == 3) {
            Account account = req.toSavingEntity(
                makeAccount,
                user.getUserNickName()+"적금 통장",
                user,
                true,
                type
            );
            savingAccountService.save(req.savingRequest(), makeAccount);
            transferService.save(req.transferRequest(), makeAccount);
            accountDao.saveAccount(account);
        }
        return "";
    }

    private String makeAccount() {
        String account = GenerateAccount.generateAccount();
        if (accountDao.compareAccount(account)) {
            return makeAccount();
        }
        return account;
    }

    // 계좌 유효성 검증
    @Override
    public AccountAmountResponse getAccountMount(String reqAccount) {
        Account account = accountDao.getAccount(reqAccount);
        if (account == null) throw new IllegalArgumentException();
        return AccountAmountResponse.from(account);
    }

    @Override
    public AccountInfoResponse getAccountInfoToCard(String reqAccount) {
        Account account = accountDao.getAccount(reqAccount);
        return AccountInfoResponse.from(account);
    }

    @Override
    public List<UserAccountResponse> findUserAccountByUserIdAndAccount(TokenInfo tokenInfo) {
        List<Account> abledAccount = accountRepository.findAccounts(UUID.fromString(tokenInfo.id()),true);
        if (abledAccount.isEmpty()) {
            return List.of();
        }
        List<AccountResponse> accountResponses = abledAccount.stream()
            .map(AccountResponse::from)
            .toList();

        return List.of(UserAccountResponse.from(accountResponses, tokenInfo));
    }

    @Override
    public AccountUserInfo getAccountFromUserId(String requestAccount, TokenInfo tokenInfo) {
        Account account = accountDao.getAccount(requestAccount);
        log.info(account.getAccount());
        return AccountUserInfo.from(account, account.getUser().getUserId().toString());
    }

    @Override
    public AccountDetailResponse getAccountInfo(TokenInfo tokenInfo, String reqAccount) {
        Account account = accountDao.getAccount(reqAccount);

        List<UserResponse> userResponses = account.getGroupAccountMembers().stream()
            .filter(GroupAccountMember::isStatus)
            .map(GroupAccountMember::getUser)
            .distinct()
            .map(user -> UserResponse.from(user.getUserNickName(), user.getUserPorfileImg(), user.getUserCode()))
            .toList();

        return AccountDetailResponse.from(account, userResponses);
    }

    @Override
    public List<AutoTransferAccountResponse> getAccounts(TokenInfo tokenInfo) {
        List<Account> accounts = accountDao.getAccountFromType(UUID.fromString(tokenInfo.id()), 1);
        return accounts.stream()
            .map(
                account ->
                    AutoTransferAccountResponse.from(account.getAccount())
            )
            .toList();
    }

    @Override
    @Transactional
    public void updateName(UUID userId, AccountNameRequest req) {
        log.info(req.account());
        Account account = accountDao.getAccount(req.account());
        account.updateName(req.accountName());
    }

    @Override
    @Transactional
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        Account account = accountDao.getAccount(req.account());
        account.updatePassword(req.accountPassword());
    }

    @Override
    @Transactional
    public void updateMoney(TokenInfo tokenInfo, AccountMoneyRequest req) {
        Account account = accountDao.getAccount(req.account());

        // 입금 받은 경우
        if (req.accountStatus().equals("deposit")) {
            Long money = account.getMoneyAmount() + req.moneyAmount();
            account.updateMoney(money);
            apiService.sendDeposit(req, account, tokenInfo.name());
        }

        // 출금한 경우
        if (req.accountStatus().equals("transfer")) {
            if (account.getMoneyAmount() <= 0) throw new InsufficientAmountException(req.moneyAmount());
            // 일회 한도를 넘었을 경우 에러
            if (req.moneyAmount() > account.getAccountOneTimeLimit()) throw new LimitTransferException(
                account.getAccountOneTimeLimit());
            long money = account.getMoneyAmount()  - req.moneyAmount();
            account.updateMoney(money);
            apiService.sendWithdraw(req, account, req.nickname());
        }
    }

    @Override
    @Transactional
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        Account account = accountDao.getAccount(req.account());
        account.updateTransferLimit(req.accountDailyLimit(), req.accountOneTimeLimit());
    }

    @Override
    @Transactional
    public void deleteAccount(TokenInfo tokenInfo, String reqAccount) {
        log.info(String.valueOf(tokenInfo.id()));
        log.info(reqAccount);
        String token = "Bearer " + jwtUtils.createToken(tokenInfo);
        Account account = accountRepository.findUserIdAndAccount(
            UUID.fromString(tokenInfo.id()),
                reqAccount)
            .orElseThrow(NotExistAccountException::new);

        List<GroupAccountMember> groupAccountMembers = groupAccountDao.getGroupAccountMember(account);

        if(!groupAccountMembers.isEmpty())
            groupAccountMembers.forEach(groupAccountDao::deleteGroupMember);
        if (account.getType() == 3) {
            savingAccountService.delete(account);
        }
        if (account.getMoneyAmount() != 0)
            throw new IllegalArgumentException("돈이 있습니다.");
        account.setAccountAble(false);
        accountRepository.save(account);
        apiService.sendAccount(token, account);
    }
}

