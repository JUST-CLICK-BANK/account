package com.click.account.domain.dto.request.account;

public record DepositRequest(
    String bhName,
    Long bhAmount,
    String myAccount,
    String bhStatus,
    Long bhBalance,
    Integer categoryId
) {
    public static DepositRequest toTranfer(AccountMoneyRequest req, String bhName, Long bhBalance) {
        return new DepositRequest(
            bhName,
            req.moneyAmount(),
            req.account(),
            "입금",
            bhBalance,
            req.category()
        );
    }
}
