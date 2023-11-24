package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/11/24-0:57
 */



import com.yubar.kedou.domain.po.Video;
import com.yubar.kedou.service.VideoService;
import com.yubar.kedou.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@ClassName VideoController
 *@Description 视频模块
 *@Author Yu-Bar
 *@Date 2023/11/24 0:57
 *@Version 1.0
 **/
@Tag(name = "视频",description = "视频相关接口")
@RequestMapping("/user/video")
@RestController
@Slf4j
public class VideoController {

    @Autowired
    private VideoService videoService;

    // TODO 按时间顺序倒序排列
    @Operation(description = "查找视频")
    @GetMapping("/list")
    public Result<List<Video>> list(){
        return Result.success(videoService.list());
    }


}
