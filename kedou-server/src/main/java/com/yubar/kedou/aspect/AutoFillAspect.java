package com.yubar.kedou.aspect;
/**
 * Author:LanBao
 * Date:2023/10/23-13:05
 */

import com.yubar.kedou.annotation.AutoFill;
import com.yubar.kedou.constant.AutoFillConstant;
import com.yubar.kedou.context.BaseContext;
import com.yubar.kedou.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @ClassName AutoFillAspect
 * @Description 自动填充公共字段
 * @Author 兰豹基
 * @Date 2023/10/23 13:05
 * @Version 1.0
 **/
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.yubar.kedou.mapper.*.*(..)) && @annotation(com.yubar.kedou.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 前置通知
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段的自动填充");
        //获取当前被拦截的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//获取方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取方法上的注解对象
        OperationType operationType = autoFill.value();//获取数据库操作类型

        //获取获取当前被拦截的数据库操作的参数...实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0)
            return;
        Object entity = args[0]; //用Object变量，因为这个参数可能是不同类型的实体

        //准备赋值数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        try {
            //根据不同的操作类型，为实体对象的公共字段赋值
            switch (operationType) {
                case INSERT -> {
                    //通过反射获取set方法
                    Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                    //通过反射为对象属性赋值
                    setCreateTime.invoke(entity, now);
                    setCreateUser.invoke(entity, currentId);
                }
                case UPDATE -> {
                    //通过反射获取set方法
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    //通过反射为对象属性赋值
                    setUpdateTime.invoke(entity, now);
                }
                case CREATE -> {
                    //通过反射获取set方法
                    Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    //通过反射为对象属性赋值
                    setCreateTime.invoke(entity, now);
                    setUpdateTime.invoke(entity, now);
                }
                case PUBLISH -> {
                    //通过反射获取set方法
                    Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    //通过反射为对象属性赋值
                    setCreateTime.invoke(entity, now);
                    setCreateUser.invoke(entity, currentId);
                    setUpdateTime.invoke(entity, now);
                }
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
