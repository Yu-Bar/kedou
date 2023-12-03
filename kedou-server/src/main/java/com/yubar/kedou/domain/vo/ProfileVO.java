package com.yubar.kedou.domain.vo;
/**
 * Author:Yu-Bar
 * Date:2023/12/1-23:37
 */

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *@ClassName ProfileVO
 *@Description TODO
 *@Author Yu-Bar
 *@Date 2023/12/1 23:37
 *@Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "查看关系列表时返回的用户数据格式")
public class ProfileVO implements Serializable {
    @Schema(description = "主键值")
    private Long id;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String profile;

    @Schema(description = "简介")
    private String bio;

    @Schema(description = "和当前用户的关系")
    private Integer relation;


    @Serial
    private static final long serialVersionUID = 1L;
}
