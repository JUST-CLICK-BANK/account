package com.click.account.domain.dto.response;

public record UserResponse(
    String userName,
    String userImg
) {
    public static UserResponse from(String userName, String userImg) {
        return new UserResponse(
            userName,
            userImg
        );
    }
}
