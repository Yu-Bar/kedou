package com.yubar.kedou.service;

import com.yubar.kedou.domain.po.Star;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yubar.kedou.domain.vo.VideoVO;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【star】的数据库操作Service
* @createDate 2023-11-24 16:47:28
*/
public interface StarService extends IService<Star> {
    /**
     * 添加收藏
     * @param videoId
     */
    void addStar(Long videoId);

    /**
     * 取消收藏
     * @param videoId
     */
    void removeStar(Long videoId);

    /**
     * 查看用户收藏列表
     * @param userId
     * @return
     */
    List<VideoVO> getStarsVideoList(Long userId);
}
