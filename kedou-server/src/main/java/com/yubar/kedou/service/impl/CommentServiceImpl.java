package com.yubar.kedou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.domain.po.Comment;
import com.yubar.kedou.service.CommentService;
import com.yubar.kedou.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 兰豹基
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-11-24 16:47:27
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




