package com.yubar.kedou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.domain.po.Likes;
import com.yubar.kedou.service.LikesService;
import com.yubar.kedou.mapper.LikesMapper;
import org.springframework.stereotype.Service;

/**
* @author 兰豹基
* @description 针对表【likes】的数据库操作Service实现
* @createDate 2023-11-24 16:47:28
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService{

}




