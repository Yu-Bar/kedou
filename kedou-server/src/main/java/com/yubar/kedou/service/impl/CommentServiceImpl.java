package com.yubar.kedou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.domain.dto.CommentCommitDTO;
import com.yubar.kedou.domain.po.Comment;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.vo.CommentVO;
import com.yubar.kedou.mapper.UserMapper;
import com.yubar.kedou.service.CommentService;
import com.yubar.kedou.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-11-24 16:47:27
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    /**
     * 根据视频 ID 查询评论列表
     * @param videoId
     * @return
     */
    @Override
    public List<CommentVO> getByVideoId(Long videoId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // TODO 目前按发布时间进行排序 后续需要改为按评论热度进行排序
        wrapper.eq(Comment::getVideoId, videoId).orderByDesc(Comment::getCreateTime);
        List<Comment> commentList = list(wrapper);
        List<CommentVO> commentVOList = BeanUtil.copyToList(commentList, CommentVO.class);
        commentVOList.forEach(commentVO -> {
            User user = userMapper.selectById(commentVO.getCreateUser());
            commentVO.setNickname(user.getNickname());
            commentVO.setProfile(user.getProfile());
        });
        return commentVOList;
    }

    /**
     * 发表评论，返回当前视频的评论列表，用于缓存
     * @param commentCommitDTO
     * @return
     */
    @Override
    public List<CommentVO> commitComment(CommentCommitDTO commentCommitDTO) {
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentCommitDTO,comment);
        commentMapper.insertComment(comment);
        return getByVideoId(comment.getVideoId());
    }
}




