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
@Schema(description = "用户查看评论返回的数据格式")
public class CommentVO implements Serializable {

    @Schema(description = "主键值")
    private Long id;

    @Schema(description = "发评论用户ID")
    private Long createUser;


    @Schema(description = "发评论用户昵称")
    private String nickname;

    @Schema(description = "发用户头像")
    private String profile;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "点踩数")
    private Long dislikes;

    @Schema(description = "评论时间")
    private LocalDateTime createTime;

}