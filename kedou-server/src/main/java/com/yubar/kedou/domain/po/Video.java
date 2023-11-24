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
 * @TableName video
 */
@TableName(value ="video")
@Data
public class Video implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 作者
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 封面地址
     */
    private String cover;

    /**
     * 描述
     */
    private String description;

    /**
     * 喜欢
     */
    private Long likes;

    /**
     * 评论
     */
    private Long comments;

    /**
     * 收藏
     */
    private Long star;

    /**
     * 分享
     */
    private Long shares;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 视频状态 0封禁 1正常
     */
    private Integer status;

    /**
     * 视频删除状态 0未删除 1删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}