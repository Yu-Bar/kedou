package com.yubar.kedou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户查看消息返回的数据格式")
public class MessageVO implements Serializable {

    @Schema(description = "会话ID")
    private Long session;

    @Schema(description = "发送人")
    private Long createUser;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "分享视频id 为空则是普通消息")
    private Long videoId;

    @Schema(description = "发送时间")
    private LocalDateTime createTime;

}