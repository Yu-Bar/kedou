package com.yubar.kedou.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户注册返回的数据格式")
public class UserSignVO implements Serializable {

    @Schema(description = "主键值")
    private Long id;

    @Schema(description = "姓名")
    private String nickname;

    @Schema(description = "头像")
    private String profile;

    @Schema(description = "jwt令牌")
    private String token;

}
