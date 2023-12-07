package com.yubar.kedou.domain.dto;
/**
 * Author:Yu-Bar
 * Date:2023/12/4-20:42
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *@ClassName MessageDTO
 *@Description 用户发送消息时传递的数据模型
 *@Author Yu-Bar
 *@Date 2023/12/4 20:42
 *@Version 1.0
 **/

@Data
@Schema(description = "用户发送消息时传递的数据模型")
public class MessageDTO {

    @Schema(description = "会话ID")
    private Long session;

    @Schema(description = "接收用户")
    private Long receiver;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "分享视频id 为空则是普通消息")
    private Long videoId;
}
