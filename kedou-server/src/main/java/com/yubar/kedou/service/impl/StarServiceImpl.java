package com.yubar.kedou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.context.BaseContext;
import com.yubar.kedou.domain.po.Star;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.po.Video;
import com.yubar.kedou.domain.vo.VideoVO;
import com.yubar.kedou.exception.PermissionException;
import com.yubar.kedou.mapper.UserMapper;
import com.yubar.kedou.mapper.VideoMapper;
import com.yubar.kedou.service.StarService;
import com.yubar.kedou.mapper.StarMapper;
import com.yubar.kedou.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author 兰豹基
* @description 针对表【star】的数据库操作Service实现
* @createDate 2023-11-24 16:47:28
*/
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star>
    implements StarService{

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    VideoService videoService;

    /**
     * 添加收藏
     * @param videoId
     */
    @Override
    public void addStar(Long videoId) {
        // 判断喜欢是否已经存在
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Star::getCreateUser, BaseContext.getCurrentId())
                .eq(Star::getVideoId, videoId);
        Star star = getOne(wrapper);
        if(star != null)
            star.setIsDelete(0);
        else{
            star = new Star();
            star.setVideoId(videoId);
            star.setCreateUser(BaseContext.getCurrentId());
            star.setCreateTime(LocalDateTime.now());
        }
        saveOrUpdate(star);
        // 添加视频收藏量 TODO 可能会出现数据不一致情况，后期通过定时任务解决？
        Video video = videoMapper.selectById(videoId);
        video.setStars(video.getStars() + 1);
        videoMapper.updateById(video);
    }

    /**
     * 取消收藏
     * @param videoId
     */
    @Override
    public void removeStar(Long videoId) {
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Star::getCreateUser,BaseContext.getCurrentId())
                .eq(Star::getVideoId, videoId);
        Star star = getOne(wrapper);
        if(star == null)
            return;
        star.setIsDelete(1);
        star.setCreateTime(LocalDateTime.now());
        updateById(star);
        // 减少视频收藏量 TODO 可能会出现数据不一致情况，后期通过定时任务解决？
        Video video = videoMapper.selectById(videoId);
        video.setStars(video.getStars() - 1);
        videoMapper.updateById(video);
    }

    /**
     * 查看用户收藏列表
     * @param userId
     * @return
     */
    @Override
    public List<VideoVO> getStarsVideoList(Long userId) {
        // 判断是否为当前用户或者用户是否开启收藏列表
        User createUser = userMapper.selectById(userId);
        if(createUser.getShowStars() != 1 && !createUser.getId().equals(BaseContext.getCurrentId()))
            throw new PermissionException(MessageConstant.STARS_NOT_VISIBLE);
        // 查询收藏视频id
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Star::getCreateUser, userId)
                .eq(Star::getIsDelete, 0);
        List<Star> starList = list(wrapper);
        if(starList == null || starList.isEmpty())
            return null;
        // 查询收藏视频列表
        List<Video> videoList = videoMapper.selectBatchIds(starList.stream().map(Star::getVideoId).toList());
        // 封装结果
        return videoService.videoList2VideoVoList(videoList);
    }
}




