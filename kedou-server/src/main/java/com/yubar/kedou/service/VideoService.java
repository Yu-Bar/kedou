package com.yubar.kedou.service;

import com.yubar.kedou.domain.dto.VideoPublishDTO;
import com.yubar.kedou.domain.po.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yubar.kedou.domain.vo.VideoVO;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【video】的数据库操作Service
* @createDate 2023-11-24 16:47:28
*/
public interface VideoService extends IService<Video> {

    /**
     * 按时间顺序倒序获取视频
     * @return
     */
    List<VideoVO> getVideoListDesc();

    /**
     * 发布视频
     * @param videoPublishDTO
     */
    void publish(VideoPublishDTO videoPublishDTO);
}
