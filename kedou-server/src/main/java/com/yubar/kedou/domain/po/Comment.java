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
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发评论用户ID
     */
    private Long createUser;

    /**
     * 视频ID
     */
    private Long videoId;

    /**
     * 内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Long likes;

    /**
     * 点踩数
     */
    private Long dislikes;

    /**
     * 0未删除 1删除
     */
    private Integer isDelete;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}