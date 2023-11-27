package com.yubar.kedou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.domain.po.Video;
import com.yubar.kedou.service.VideoService;
import com.yubar.kedou.mapper.VideoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【video】的数据库操作Service实现
* @createDate 2023-11-24 16:47:28
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

    @Override
    public List<Video> getVideoListDesc() {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Video::getCreateTime);
        return list(wrapper);
    }
}




