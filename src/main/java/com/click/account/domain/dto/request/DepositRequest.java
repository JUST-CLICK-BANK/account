package com.click.account.domain.dto.request;

public record DepositRequest(
    String bhName,
    Long bhAmount,
    String myAccount,
    String yourAccount,
    String bhStatus,
    Long bhBalance,
    Long categoryId
) {
    public static DepositRequest toTranfer(AccountMoneyRequest req, String bhName, Long bhBalance) {
        return new DepositRequest(bhName, req.moneyAmount(), req.account(), req.transferAccount(), "입금", bhBalance, req.category());
    }
}
