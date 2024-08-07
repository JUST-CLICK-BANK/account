package com.click.account.config.constants;

public enum AccountType {
    ACCOUNT(1),
    GROUP(2),
    SAVING(3);

    private final Integer accountType;

    AccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public static Integer fromString(String value) {
        return switch (value.toUpperCase()) {
            case "ACCOUNT" -> ACCOUNT.getAccountType();
            case "GROUP" -> GROUP.getAccountType();
            case "SAVING" -> SAVING.getAccountType();
            default -> throw new IllegalArgumentException("Unknown account type value: " + value);
        };
    }
}
