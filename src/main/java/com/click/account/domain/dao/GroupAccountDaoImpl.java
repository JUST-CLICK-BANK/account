package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.entity.GroupAccount;
import com.click.account.domain.repository.GroupAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GroupAccountDaoImpl implements GroupAccountDao{
    private final GroupAccountRepository groupAccountRepository;

    @Override
    public void saveGroupToUser(TokenInfo tokenInfo, String account, UUID userId) {
        boolean checkAdmin = !groupAccountRepository.existsByAccountAndAdminIsTrue(account);

        groupAccountRepository.save(
                GroupAccount.builder()
                        .account(account)
                        .userId(userId)
                        .userNickName(tokenInfo.name())
                        .userPofileImg(tokenInfo.img())
                        .admin(checkAdmin)
                        .build()
        );
    }
}
