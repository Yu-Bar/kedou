package com.yubar.kedou.domain.vo;

import com.yubar.kedou.domain.po.Video;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "查看用户返回数据格式")
public class UserVO implements Serializable {

    @Schema(description = "主键值")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "喜欢")
    private Long likes;

    @Schema(description = "朋友")
    private Long friends;

    @Schema(description = "关注")
    private Long following;

    @Schema(description = "粉丝")
    private Long follower;

    @Schema(description = "头像")
    private String profile;

    @Schema(description = "简介")
    private String bio;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "学校")
    private String school;

    @Schema(description = "账号状态")
    private Integer status;

    @Schema(description = "视频列表")
    private List<Video> videoList;

}
