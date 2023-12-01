package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/12/1-15:34
 */

import com.yubar.kedou.domain.vo.VideoVO;
import com.yubar.kedou.result.Result;
import com.yubar.kedou.service.StarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@ClassName StarsController
 *@Description 收藏模块
 *@Author Yu-Bar
 *@Date 2023/12/1 15:34
 *@Version 1.0
 **/
@Tag(name = "收藏",description = "收藏相关接口")
@RestController
@RequestMapping("/user/star")
@Slf4j
public class StarsController {

    @Autowired
    private StarService starService;

    @Operation(description = "收藏视频")
    @PostMapping
    public Result likes(Long videoId){
        starService.addStar(videoId);
        return Result.success();
    }

    @Operation(description = "取消收藏视频")
    @DeleteMapping
    public Result dislikes(Long videoId){
        starService.removeStar(videoId);
        return Result.success();
    }

    @Operation(description = "查看用户收藏视频列表")
    @GetMapping("/list")
    public Result<List<VideoVO>> list(Long userId){
        List<VideoVO> videoVOList = starService.getStarsVideoList(userId);
        return Result.success(videoVOList);
    }
}
