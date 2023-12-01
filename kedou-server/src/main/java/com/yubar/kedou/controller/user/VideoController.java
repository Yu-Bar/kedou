package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/11/24-0:57
 */



import com.yubar.kedou.domain.dto.VideoPublishDTO;
import com.yubar.kedou.domain.po.Video;
import com.yubar.kedou.domain.vo.VideoVO;
import com.yubar.kedou.service.VideoService;
import com.yubar.kedou.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 拉取视频列表 需要拉取视频是否已收藏或已喜欢
     * @return
     */
    @Operation(description = "查找视频")
    @GetMapping("/list")
    public Result<List<VideoVO>> list(){
        List<VideoVO> videoVoList = videoService.getVideoListDesc();
        return Result.success(videoVoList);
    }

    /**
     * 上传视频
     * @return
     */
    @Operation(description = "上传视频")
    @PostMapping("/upload")
    public Result upload(@RequestBody VideoPublishDTO videoPublishDTO){
        videoService.publish(videoPublishDTO);
        return Result.success();
    }


}
