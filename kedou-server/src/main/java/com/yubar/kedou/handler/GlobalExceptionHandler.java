package com.yubar.kedou.handler;

import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.exception.BaseException;
import com.yubar.kedou.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理sql异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //通过异常信息进行处理
        //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        //TODO 其他sql重复报错也是 账户已存在
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ACCOUNT_ALREADY_EXIST;
            return Result.error(msg);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

}
