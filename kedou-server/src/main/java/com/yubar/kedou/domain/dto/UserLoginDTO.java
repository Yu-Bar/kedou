package com.yubar.kedou.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "员工登录时传递的数据模型")
public class UserLoginDTO implements Serializable {

    @Schema(description = "账户(username/phone)")
    private String account;

    @Schema(description = "密码")
    private String password;

}
