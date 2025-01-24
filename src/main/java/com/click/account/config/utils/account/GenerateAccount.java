package com.click.account.config.utils.account;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

public class GenerateAccount {
    // Click 계좌 시작번호 907-000-000000
    private static final String CLICKBANKACCOUNT = "907";

    public static String generateAccount() {
        String account = RandomStringUtils.random(9, 48, 57, false, true);
        return CLICKBANKACCOUNT + account;
    }
}
