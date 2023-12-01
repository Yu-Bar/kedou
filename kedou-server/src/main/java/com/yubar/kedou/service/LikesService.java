package com.yubar.kedou.service;

import com.yubar.kedou.domain.po.Likes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yubar.kedou.domain.vo.VideoVO;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【likes】的数据库操作Service
* @createDate 2023-11-24 16:47:28
*/
public interface LikesService extends IService<Likes> {

    /**
     * 添加喜欢
     * @param videoId
     */
    void addLike(Long videoId);

    /**
     * 取消喜欢
     * @param videoId
     */
    void removeLike(Long videoId);

    /**
     * 查看用户喜欢列表
     * @param userId
     * @return
     */
    List<VideoVO> getLikesVideoList(Long userId);
}
