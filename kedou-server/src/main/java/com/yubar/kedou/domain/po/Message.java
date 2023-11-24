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
 * @TableName message
 */
@TableName(value ="message")
@Data
public class Message implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送人
     */
    private Long sender;

    /**
     * 接收人
     */
    private Long receiver;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 分享视频id
     */
    private Long videoId;

    /**
     * 发送时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}