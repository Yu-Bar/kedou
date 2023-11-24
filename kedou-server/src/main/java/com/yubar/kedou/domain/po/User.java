package com.yubar.kedou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String profile;

    /**
     * 
     */
    private Long likes;

    /**
     * 朋友
     */
    private Long friends;

    /**
     * 关注
     */
    private Long following;

    /**
     * 粉丝
     */
    private Long follower;

    /**
     * 简介
     */
    private String bio;

    /**
     * 
     */
    private String address;

    /**
     * 男0 女1
     */
    private Integer sex;

    /**
     * 
     */
    private Date birthday;

    /**
     * 
     */
    private String school;

    /**
     * 账号状态 0锁定 1正常
     */
    private Integer status;

    /**
     * 注册时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}