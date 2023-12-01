package com.yubar.kedou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.context.BaseContext;
import com.yubar.kedou.domain.po.Likes;
import com.yubar.kedou.domain.po.Star;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.po.Video;
import com.yubar.kedou.domain.vo.VideoVO;
import com.yubar.kedou.exception.PermissionException;
import com.yubar.kedou.mapper.StarMapper;
import com.yubar.kedou.mapper.UserMapper;
import com.yubar.kedou.mapper.VideoMapper;
import com.yubar.kedou.service.LikesService;
import com.yubar.kedou.mapper.LikesMapper;
import com.yubar.kedou.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hutool.crypto.CipherMode.wrap;

/**
* @author 兰豹基
* @description 针对表【likes】的数据库操作Service实现
* @createDate 2023-11-24 16:47:28
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService{

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    StarMapper starMapper;
    @Autowired
    VideoService videoService;

    /**
     * 添加喜欢
     * @param videoId
     */
    @Override
    public void addLike(Long videoId) {
        // 判断喜欢是否已经存在
        LambdaQueryWrapper<Likes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Likes::getCreateUser,BaseContext.getCurrentId())
                .eq(Likes::getVideoId, videoId);
        Likes likes = getOne(wrapper);
        if(likes != null)
            likes.setIsDelete(0);
        else{
            likes = new Likes();
            likes.setVideoId(videoId);
            likes.setCreateUser(BaseContext.getCurrentId());
            likes.setCreateTime(LocalDateTime.now());
        }
        saveOrUpdate(likes);
        // 添加视频喜欢量 TODO 可能会出现数据不一致情况，后期通过定时任务解决？
        Video video = videoMapper.selectById(videoId);
        video.setLikes(video.getLikes() + 1);
        videoMapper.updateById(video);
        // 添加用户获赞量 TODO 可能会出现数据不一致情况，后期通过定时任务解决？
        User user = userMapper.selectById(video.getCreateUser());
        user.setLikes(user.getLikes() + 1);
        userMapper.updateById(user);
    }

    /**
     * 取消喜欢
     * @param videoId
     */
    @Override
    public void removeLike(Long videoId) {
        LambdaQueryWrapper<Likes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Likes::getCreateUser,BaseContext.getCurrentId())
                .eq(Likes::getVideoId, videoId);
        Likes likes = getOne(wrapper);
        if(likes == null)
            return;
        likes.setIsDelete(1);
        likes.setCreateTime(LocalDateTime.now());
        updateById(likes);
        // 减少视频喜欢量 TODO 可能会出现数据不一致情况，后期通过定时任务解决？
        Video video = videoMapper.selectById(videoId);
        video.setLikes(video.getLikes() - 1);
        videoMapper.updateById(video);
        // 减少用户获赞量 TODO 可能会出现数据不一致情况，后期通过定时任务解决？
        User user = userMapper.selectById(video.getCreateUser());
        user.setLikes(user.getLikes() - 1);
        userMapper.updateById(user);
    }

    /**
     * 查看用户喜欢列表
     * @param userId
     * @return
     */
    @Override
    public List<VideoVO> getLikesVideoList(Long userId) {
        // 判断是否为当前用户或者用户是否开启喜欢列表
        User createUser = userMapper.selectById(userId);
        if(createUser.getShowLikes() != 1 && !createUser.getId().equals(BaseContext.getCurrentId()))
            throw new PermissionException(MessageConstant.LIKES_NOT_VISIBLE);
        // 查询喜欢视频id
        LambdaQueryWrapper<Likes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Likes::getCreateUser, userId)
                .eq(Likes::getIsDelete, 0);
        List<Likes> likesList = list(wrapper);
        if(likesList == null || likesList.isEmpty())
            return null;
        // 查询喜欢视频列表
        List<Video> videoList = videoMapper.selectBatchIds(likesList.stream().map(Likes::getVideoId).toList());
        // 封装结果
        return videoService.videoList2VideoVoList(videoList);
    }
}




