package com.yubar.kedou.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 在 MQ 中传输的信息类型
 */

@Data
@Schema(description = "推送消息至 MQ 的数据模型")
public class MessageMQDTO implements Serializable{

    @Schema(description = "会话ID")
    private Long session;

    @Schema(description = "发送人")
    private Long createUser;

    @Schema(description = "接收用户")
    private Long receiver;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "分享视频id 为空则是普通消息")
    private Long videoId;

    @Schema(description = "发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}