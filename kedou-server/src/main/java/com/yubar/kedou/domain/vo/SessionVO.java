package com.yubar.kedou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户查看会话返回的数据格式")
public class SessionVO implements Serializable {

    @Schema(description = "会话ID")
    private Long session;

    @Schema(description = "聊天的对方用户id")
    private Long userId;

    @Schema(description = "聊天的对方用户昵称")
    private String nickname;

    @Schema(description = "聊天的对方用户头像")
    private String profile;

    @Schema(description = "消息列表")
    private List<MessageVO> messageList;

}