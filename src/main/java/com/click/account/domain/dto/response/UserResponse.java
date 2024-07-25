package com.click.account.domain.dto.response;

public record UserResponse(
    String userName,
    String userImg,
    String userCode
) {
    public static UserResponse from(String userName, String userImg, String userCode) {
        return new UserResponse(
            userName,
            userImg,
            userCode
        );
    }
}
