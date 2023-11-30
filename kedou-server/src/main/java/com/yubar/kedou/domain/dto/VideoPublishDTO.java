package com.yubar.kedou.domain.dto;
/**
 * Author:Yu-Bar
 * Date:2023/11/30-0:07
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 *@ClassName VideoPublishDTO
 *@Description 用户上传视频传递的数据模型
 *@Author Yu-Bar
 *@Date 2023/11/30 0:07
 *@Version 1.0
 **/
@Data
@Schema(description = "用户上传视频传递的数据模型")
public class VideoPublishDTO {
    

@Schema(description = "标题")
    private String title;

@Schema(description = "视频地址")
    private String url;

@Schema(description = "封面地址")
    private String cover;

@Schema(description = "描述")
    private String description;

@Schema(description = "标签名列表")
    private List<String> label;

}
