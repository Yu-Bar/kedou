package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/12/1-17:08
 */

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yubar.kedou.constant.RelationConstant;
import com.yubar.kedou.domain.po.Relation;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.vo.ProfileVO;
import com.yubar.kedou.result.Result;
import com.yubar.kedou.service.RelationService;
import com.yubar.kedou.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *@ClassName FriendController
 *@Description 用户关系模块
 *@Author Yu-Bar
 *@Date 2023/12/1 17:08
 *@Version 1.0
 **/

@Tag(name = "用户关系",description = "用户关系相关接口")
@RestController
@RequestMapping("/user/relation")
@Slf4j
public class RelationController {

    @Autowired
    private RelationService relationService;

    @Operation(description = "查看用户关注列表")
    @GetMapping("/following/{userId}")
    public Result<Set<ProfileVO>> following(@PathVariable("userId") Long userId){
        Set<ProfileVO> profileVOSet = relationService.getFollowingSet(userId);
        relationService.setRelationWithCurrentUser(profileVOSet);
        return Result.success(profileVOSet);
    }

    @Operation(description = "查看用户粉丝列表")
    @GetMapping("/follower/{userId}")
    public Result<Set<ProfileVO>> follower(@PathVariable("userId") Long userId){
        Set<ProfileVO> profileVOSet= relationService.getFollowerSet(userId);
        relationService.setRelationWithCurrentUser(profileVOSet);
        return Result.success(profileVOSet);
    }

    @Operation(description = "查看用户朋友列表")
    @GetMapping("/friend/{userId}")
    public Result<Set<ProfileVO>> friend(@PathVariable("userId") Long userId){
        Set<ProfileVO> profileVOSet = relationService.getFriendSet(userId);
        relationService.setRelationWithCurrentUser(profileVOSet);
        return Result.success(profileVOSet);
    }

    @Operation(description = "添加关注")
    @PostMapping("/follow/{userId}")
    public Result follow(@PathVariable("userId") Long userId){
        relationService.follow(userId);
        return Result.success();
    }

    @Operation(description = "取消关注")
    @DeleteMapping("/follow/{userId}")
    public Result unfollow(@PathVariable("userId") Long userId){
        relationService.unfollow(userId);
        return Result.success();
    }
}
