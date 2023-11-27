package com.yubar.kedou.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户注册时传递的数据模型")
public class UserSignDTO implements Serializable {

    @Schema(description = "账户(username/phone)")
    private String account;

    @Schema(description = "密码")
    private String password;

}
