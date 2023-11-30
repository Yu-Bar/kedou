package com.yubar.kedou.mapper;

import com.yubar.kedou.annotation.AutoFill;
import com.yubar.kedou.domain.po.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yubar.kedou.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;

/**
* @author 兰豹基
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-11-30 16:22:24
* @Entity com.yubar.kedou.domain.po.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 发布评论
     * @param comment
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into comment(create_user, video_id, content, create_time ) values (#{createUser}, #{videoId}, #{content}, #{createTime} )")
    void insertComment(Comment comment);
}




