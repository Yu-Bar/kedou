package com.yubar.kedou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "聊天界面下需要的用户数据格式")
public class UserChatVO implements Serializable {

    @Schema(description = "主键值")
    private Long id;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String profile;

}
