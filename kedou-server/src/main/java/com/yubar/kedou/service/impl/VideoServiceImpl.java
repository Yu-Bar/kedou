package com.yubar.kedou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.constant.VideoStatusConstant;
import com.yubar.kedou.context.BaseContext;
import com.yubar.kedou.domain.dto.VideoPublishDTO;
import com.yubar.kedou.domain.po.Likes;
import com.yubar.kedou.domain.po.Star;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.po.Video;
import com.yubar.kedou.domain.vo.VideoVO;
import com.yubar.kedou.mapper.LikesMapper;
import com.yubar.kedou.mapper.StarMapper;
import com.yubar.kedou.mapper.UserMapper;
import com.yubar.kedou.service.UserService;
import com.yubar.kedou.service.VideoService;
import com.yubar.kedou.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 兰豹基
 * @description 针对表【video】的数据库操作Service实现
 * @createDate 2023-11-24 16:47:28
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
        implements VideoService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private LikesMapper likesMapper;
    @Autowired
    private StarMapper starMapper;

    /**
     * 按时间顺序倒序获取视频
     * @return
     */
    @Override
    public List<VideoVO> getVideoListDesc() {
        // 查找视频
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getIsDelete, VideoStatusConstant.UN_DELETE)
                .orderByDesc(Video::getCreateTime);
        List<Video> videoList = list(wrapper);
        // 封装对象
        return videoList2VideoVoList(videoList);
    }

    /**
     * 封装VO对象
     * @param videoList
     * @return
     */
    public List<VideoVO> videoList2VideoVoList(List<Video> videoList){
        List<VideoVO> videoVOList = BeanUtil.copyToList(videoList, VideoVO.class);
        videoVOList.forEach(videoVO -> {
            User user = userMapper.selectById(videoVO.getCreateUser());
            videoVO.setCreateUser(user.getId());
            videoVO.setProfile(user.getProfile());
            videoVO.setNickname(user.getNickname());
            if(BaseContext.getCurrentId() != null){
                // 查看视频喜欢状态
                LambdaQueryWrapper<Likes> likesWrapper = new LambdaQueryWrapper<>();
                likesWrapper.eq(Likes::getCreateUser, BaseContext.getCurrentId());
                likesWrapper.eq(Likes::getVideoId, videoVO.getId());
                Likes likes = likesMapper.selectOne(likesWrapper);
                if(likes != null && likes.getIsDelete() == 0)
                    videoVO.setIsLike(true);
                // 查看视频收藏状态
                LambdaQueryWrapper<Star> starWrapper = new LambdaQueryWrapper<>();
                starWrapper.eq(Star::getCreateUser, BaseContext.getCurrentId());
                starWrapper.eq(Star::getVideoId, videoVO.getId());
                Star star = starMapper.selectOne(starWrapper);
                if(star != null && star.getIsDelete() == 0)
                    videoVO.setIsStar(true);
            }
        });
        return videoVOList;
    }

    /**
     * 发布视频
     * @param videoPublishDTO
     */
    @Override
    public void publish(VideoPublishDTO videoPublishDTO) {
        Video video = new Video();
        BeanUtil.copyProperties(videoPublishDTO, video,"label");
        // label 在数据库中用空格分隔 要去掉标签前面的#
        video.setLabel(videoPublishDTO.getLabel().stream()
                .map(s -> s.replaceFirst("#", ""))
                .collect(Collectors.joining(" ")));
        // TODO 当前没有审核、删除和私密操作，后期需要修改
        video.setStatus(VideoStatusConstant.PUBLISH);
        video.setIsDelete(VideoStatusConstant.UN_DELETE);
        video.setOpen(VideoStatusConstant.OPEN);
        videoMapper.insertNewVideo(video);
    }
}




