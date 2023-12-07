package com.yubar.kedou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户提交评论时传递的数据模型")
public class CommentCommitDTO implements Serializable {

    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "内容")
    private String content;


}