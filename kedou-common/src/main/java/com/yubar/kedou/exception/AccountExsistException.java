package com.yubar.kedou.exception;

/**
 * 账号不存在异常
 */
public class AccountExsistException extends BaseException {

    public AccountExsistException() {
    }

    public AccountExsistException(String msg) {
        super(msg);
    }

}
