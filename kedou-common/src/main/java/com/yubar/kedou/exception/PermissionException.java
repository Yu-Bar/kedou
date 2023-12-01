package com.yubar.kedou.exception;
/**
 * Author:Yu-Bar
 * Date:2023/12/1-2:06
 */

/**
 *@ClassName PermissionException
 *@Description 权限不允许异常
 *@Author Yu-Bar
 *@Date 2023/12/1 2:06
 *@Version 1.0
 **/
public class PermissionException extends BaseException{
    public PermissionException() {
    }

    public PermissionException(String msg) {
        super(msg);
    }
}
