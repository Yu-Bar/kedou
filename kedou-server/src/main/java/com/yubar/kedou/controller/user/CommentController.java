package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/11/30-15:27
 */


import com.yubar.kedou.domain.dto.CommentCommitDTO;
import com.yubar.kedou.domain.vo.CommentVO;
import com.yubar.kedou.result.Result;
import com.yubar.kedou.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@ClassName CommentController
 *@Description 评论接口
 *@Author Yu-Bar
 *@Date 2023/11/30 15:27
 *@Version 1.0
 **/
@Tag(name = "评论",description = "评论相关接口")
@RestController
@RequestMapping("/user/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Operation(description = "查看评论")
    @GetMapping("/list/{videoId}")
    @Cacheable(value = "comment",key = "#videoId")
    public Result<List<CommentVO>> list(@PathVariable Long videoId){
        List<CommentVO> commentVOList = commentService.getByVideoId(videoId);
        return Result.success(commentVOList);
    }

    @Operation(description = "提交评论")
    @PostMapping("/commit")
    @CachePut(value = "comment",key = "#commentCommitDTO.videoId")
    public Result<List<CommentVO>> commit(@RequestBody CommentCommitDTO commentCommitDTO){
        List<CommentVO> commentVOList = commentService.commitComment(commentCommitDTO);
        return Result.success(commentVOList);
    }
}
