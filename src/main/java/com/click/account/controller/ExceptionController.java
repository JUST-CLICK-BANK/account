package com.click.account.controller;

import com.click.account.config.exception.InsufficientAmountException;
import com.click.account.config.exception.LimitTransferException;
import com.click.account.config.exception.NotExistAccountException;
import com.click.account.config.exception.GroupAccountMemeberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotExistAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String existAccountException() {
        return "All Ready Exist Account";
    }

    @ExceptionHandler(GroupAccountMemeberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String groupAccountMemeberException() {
        return "Not Found GroupAccountMember";
    }

    @ExceptionHandler(InsufficientAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String insufficientAmountException(InsufficientAmountException e) {
        return e.getMessage() + "InsufficientAmountException";
    }

    @ExceptionHandler(LimitTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String limitTransferException(LimitTransferException e) {
        return e.getMessage() + "LimitTransferException";
    }
}
