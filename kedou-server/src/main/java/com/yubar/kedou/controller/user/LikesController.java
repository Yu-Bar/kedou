package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/12/1-1:21
 */
import com.yubar.kedou.domain.vo.VideoVO;
import com.yubar.kedou.result.Result;
import com.yubar.kedou.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@ClassName LikesController
 *@Description 喜欢模块
 *@Author Yu-Bar
 *@Date 2023/12/1 1:21
 *@Version 1.0
 **/

@Tag(name = "喜欢",description = "喜欢相关接口")
@RestController
@RequestMapping("/user/likes")
@Slf4j
public class LikesController {

    @Autowired
    LikesService likesService;

    @Operation(description = "喜欢视频")
    @PostMapping
    public Result likes(Long videoId){
        likesService.addLike(videoId);
        return Result.success();
    }

    @Operation(description = "喜欢视频")
    @DeleteMapping
    public Result dislikes(Long videoId){
        likesService.removeLike(videoId);
        return Result.success();
    }

    @Operation(description = "查看喜欢视频")
    @GetMapping("/list")
    public Result<List<VideoVO>> list(Long userId){
        List<VideoVO> videoVOList = likesService.getLikesVideoList(userId);
        return Result.success(videoVOList);
    }
}
