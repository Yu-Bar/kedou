package com.yubar.kedou.handler;

import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.exception.BaseException;
import com.yubar.kedou.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxUploadSize;

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理sql异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        //通过异常信息进行处理
        //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        //TODO 其他sql重复报错也是 账户已存在
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ACCOUNT_ALREADY_EXIST;
            return Result.error(msg);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 上传文件大小超过上限
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(MaxUploadSizeExceededException ex) {
        String msg = MessageConstant.LIMIT_SIZE + maxUploadSize;
        return Result.error(msg);
//        if(message.contains("The field file exceeds its maximum permitted size")){
//            // 定义匹配数字的正则表达式，只匹配特定段落中的数字部分
//            String regex = "The field file exceeds its maximum permitted size of (\\d+) bytes\\.";
//            // 编译正则表达式模式
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(message);
//            Long limitSize = null;
//            // 寻找匹配项并返回数值类型
//            if (matcher.find()) {
//                String matchedNumber = matcher.group(1); // 使用捕获组索引 1 来获取括号中的匹配部分
//                limitSize = Long.parseLong(matchedNumber);
//                limitSize /= 1024 * 1024;
//            }
//            String msg = MessageConstant.LIMIT_SIZE + limitSize + "MB";
//            return Result.error(msg);
//        }
//        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

}
