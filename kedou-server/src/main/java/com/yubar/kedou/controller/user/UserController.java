package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/11/24-15:22
 */

import com.yubar.kedou.constant.JwtClaimsConstant;
import com.yubar.kedou.domain.dto.UserLoginDTO;
import com.yubar.kedou.domain.dto.UserSignDTO;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.vo.UserLoginVO;
import com.yubar.kedou.domain.vo.UserSignVO;
import com.yubar.kedou.domain.vo.UserVO;
import com.yubar.kedou.service.UserService;
import com.yubar.kedou.properties.JwtProperties;
import com.yubar.kedou.result.Result;
import com.yubar.kedou.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName UserController
 *@Description 用户模块
 *@Author Yu-Bar
 *@Date 2023/11/24 15:22
 *@Version 1.0
 **/
@Tag(name = "用户",description = "用户相关接口")
@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;


    @Operation(description = "用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    @Operation(description = "用户注册")
    @PostMapping("/sign")
    public Result<UserSignVO> sign(@RequestBody UserSignDTO userSignDTO) {
        log.info("用户注册：{}", userSignDTO);

        User user = userService.sign(userSignDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserSignVO userSignVO = UserSignVO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .token(token)
                .build();

        return Result.success(userSignVO);
    }

    @Operation(description = "查看用户")
    @GetMapping("/{userId}")
    public Result<UserVO> getUserInfo(@PathVariable("userId") Long userId) {
        log.info("用户查询：{}", userId);
        UserVO uservo = userService.getInfoById(userId);

        return Result.success(uservo);
    }
}
