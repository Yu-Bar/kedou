package com.yubar.kedou.domain.vo;

import com.yubar.kedou.domain.po.Video;
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
@Schema(description = "查看用户返回数据格式")
public class VideoVO implements Serializable {

    @Schema(description = "主键值")
    private Long id;

   @Schema(description = "作者")
    private Long createUser;

    @Schema(description = "作者昵称")
    private String nickname;

    @Schema(description = "作者头像")
    private String profile;

   @Schema(description = "标题")
    private String title;

   @Schema(description = "视频地址")
    private String url;

   @Schema(description = "封面地址")
    private String cover;

   @Schema(description = "描述")
    private String description;

   @Schema(description = "喜欢")
    private Long likes;

   @Schema(description = "评论")
    private Long comments;

   @Schema(description = "收藏")
    private Long stars;

   @Schema(description = "分享")
    private Long shares;

   @Schema(description = "标签名列表（以空格分隔）")
    private String label;

   @Schema(description = "发布时间")
    private LocalDateTime createTime;

   @Schema(description = "更新时间")
    private LocalDateTime updateTime;

   @Schema(description = "视频状态 0待审核 1审核未通过 2已发布 3封禁 ")
    private Integer status;

//   @Schema(description = "视频删除状态 0未删除 1删除")
//    private Integer isDelete;

   @Schema(description = "公开状态 0私密 1公开")
    private Integer open;

}
