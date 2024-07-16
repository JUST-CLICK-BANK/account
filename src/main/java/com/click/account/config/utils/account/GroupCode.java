package com.click.account.config.utils.account;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class GroupCode {

    public static String getGroupCode() {
        return RandomStringUtils.random(6, true, true);
    }
}
