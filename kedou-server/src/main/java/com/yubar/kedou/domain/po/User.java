package com.yubar.kedou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * 头像
     */
    private String profile;

    /**
     * 获赞
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
    private LocalDate birthday;

    /**
     * 
     */
    private String school;

    /**
     * 账号状态 0封禁1正常 2信息待完善
     */
    private Integer status;

    /**
     * 账号是否删除 0正常 1销号
     */
    private Integer isDelete;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否展示粉丝列表 0不展示 1展示
     */
    private Integer showFollowed;

    /**
     * 是否展示关注列表 0不展示 1展示
     */
    private Integer showFollowing;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}