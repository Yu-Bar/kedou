package com.yubar.kedou.service;

import com.yubar.kedou.domain.po.Relation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yubar.kedou.domain.vo.ProfileVO;
import com.yubar.kedou.domain.vo.UserVO;

import java.util.List;
import java.util.Set;

/**
* @author 兰豹基
* @description 针对表【relation】的数据库操作Service
* @createDate 2023-12-01 23:48:59
*/
public interface RelationService extends IService<Relation> {

    /**
     * 查看用户关注列表
     * @param userId
     * @return
     */
    Set<ProfileVO> getFollowingSet(Long userId);

    /**
     * 查看用户粉丝列表
     * @param userId
     * @return
     */
    Set<ProfileVO> getFollowerSet(Long userId);

    /**
     * 查看朋友列表
     * @param userId
     * @return
     */
    Set<ProfileVO> getFriendSet(Long userId);

    /**
     * 添加关注
     * @param userId
     */
    void follow(Long userId);


    /**
     * 取消关注
     * @param userId
     */
    void unfollow(Long userId);

    /**
     * 获取并设置和当前用户的关系
     * @param profileVOSet
     */
    void setRelationWithCurrentUser(Set<ProfileVO> profileVOSet);


    /**
     * 获取并设置和当前用户的关系
     * @param userVO
     */
    void setRelationWithCurrentUser(UserVO userVO);
}
