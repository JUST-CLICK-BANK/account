package com.click.account.config.utils.account;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

public class GroupCode {

    public static String getGroupCode() {
        String groupCode;
        do {
            groupCode = RandomStringUtils.random(6, true, true).toUpperCase();
        } while (!isNumber(groupCode));
        return groupCode;
    }

    private static Boolean isNumber(String groupCode) {
        for(char c : groupCode.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
