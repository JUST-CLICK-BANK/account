package com.click.account.config.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GroupCode {

    public static String getGroupCode() {
        return RandomStringUtils.random(6, true, true);
    }
}
