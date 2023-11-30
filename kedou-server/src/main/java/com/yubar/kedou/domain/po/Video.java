package com.yubar.kedou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName video
 */
@TableName(value ="video")
@Data
public class Video implements Serializable {
    /**
     * 视频ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 作者
     */
    private Long createUser;

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
    private Long stars;

    /**
     * 分享
     */
    private Long shares;

    /**
     * 标签名列表（以空格分隔）
     */
    private String label;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 视频状态 0待审核 1审核未通过 2已发布 3封禁 
     */
    private Integer status;

    /**
     * 视频删除状态 0未删除 1删除
     */
    private Integer isDelete;

    /**
     * 公开状态 0私密 1公开
     */
    private Integer open;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}