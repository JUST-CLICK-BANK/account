package com.click.account.domain.dto.response;

import com.click.account.config.utils.jwt.TokenInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record UserAccountResponse(
    List<AccountResponse> accounts,
    String userName,
    String userImg
) {
    public static UserAccountResponse from(List<AccountResponse> accountResponses, TokenInfo tokenInfo) {
        return UserAccountResponse.builder()
                .accounts(accountResponses)
                .userName(tokenInfo.name())
                .userImg(tokenInfo.img())
                .build();
    }
}
