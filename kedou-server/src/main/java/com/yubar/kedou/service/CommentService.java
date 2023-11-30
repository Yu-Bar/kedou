package com.yubar.kedou.service;

import com.yubar.kedou.domain.dto.CommentCommitDTO;
import com.yubar.kedou.domain.po.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yubar.kedou.domain.vo.CommentVO;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-11-24 16:47:27
*/
public interface CommentService extends IService<Comment> {

    /**
     * 根据视频 ID 查询评论列表
     * @param videoId
     * @return
     */
    List<CommentVO> getByVideoId(Long videoId);

    /**
     * 发表评论
     * @param commentCommitDTO
     * @return
     */
    List<CommentVO> commitComment(CommentCommitDTO commentCommitDTO);
}
