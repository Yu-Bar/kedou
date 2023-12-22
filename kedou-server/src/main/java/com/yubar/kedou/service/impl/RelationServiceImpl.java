package com.yubar.kedou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.constant.RelationConstant;
import com.yubar.kedou.context.BaseContext;
import com.yubar.kedou.domain.po.Relation;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.vo.ProfileVO;
import com.yubar.kedou.domain.vo.UserVO;
import com.yubar.kedou.exception.FollowException;
import com.yubar.kedou.exception.PermissionException;
import com.yubar.kedou.mapper.UserMapper;
import com.yubar.kedou.service.RelationService;
import com.yubar.kedou.mapper.RelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author 兰豹基
* @description 针对表【relation】的数据库操作Service实现
* @createDate 2023-12-01 23:48:59
*/
@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation>
    implements RelationService{

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<String,ProfileVO> redisTemplate;

    /**
     * 查看用户关注列表
     * @param userId
     * @return
     */
    @Override
    public Set<ProfileVO> getFollowingSet(Long userId) {
        // 检查列表是否可看
        User user = userMapper.selectById(userId);
        if(user.getShowFollowing().equals(0) && !userId.equals(BaseContext.getCurrentId()))
            throw new PermissionException(MessageConstant.FOLLOWING_NOT_VISIBLE);
        // 检查缓存
        Set<ProfileVO> profileVOSet = redisTemplate.opsForSet().members("following::" + userId);
        if(profileVOSet == null || profileVOSet.isEmpty()){
            // 查找关注列表
            LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<Relation>()
                    .eq(Relation::getCreateUser, userId)
                    .eq(Relation::getIsDelete, 0);
            List<Relation> relationList = list(wrapper);
            List<ProfileVO> profileVOList;
            if(!relationList.isEmpty()){
                List<User> userList = userMapper.selectBatchIds(relationList.stream().map(Relation::getFollowId).toList());
                profileVOList = BeanUtil.copyToList(userList, ProfileVO.class);
            }else {
                profileVOList = new ArrayList<ProfileVO>();
            }
            profileVOSet = new HashSet<>(profileVOList);
            for (ProfileVO profileVO : profileVOSet) {
                redisTemplate.opsForSet().add("following::" + userId, profileVO);
            }
        }
        return profileVOSet;
    }

    /**
     * 查看用户粉丝列表
     * @param userId
     * @return
     */
    @Override
    public  Set<ProfileVO> getFollowerSet(Long userId) {
        // 检查列表是否可看
        User user = userMapper.selectById(userId);
        if(user.getShowFollowing().equals(0) && !userId.equals(BaseContext.getCurrentId()))
            throw new PermissionException(MessageConstant.FOLLOWER_NOT_VISIBLE);
        // 检查缓存
        Set<ProfileVO> profileVOSet = redisTemplate.opsForSet().members("follower::" + userId);
        if(profileVOSet == null || profileVOSet.isEmpty()){
            // 查找粉丝列表
            LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<Relation>()
                    .eq(Relation::getFollowId, userId)
                    .eq(Relation::getIsDelete, 0);
            List<Relation> relationList = list(wrapper);
            List<ProfileVO> profileVOList;
            if(!relationList.isEmpty()){
                List<User> userList = userMapper.selectBatchIds(relationList.stream().map(Relation::getCreateUser).toList());
                profileVOList = BeanUtil.copyToList(userList, ProfileVO.class);
            }else {
                profileVOList = new ArrayList<ProfileVO>();
            }
            profileVOSet = new HashSet<>(profileVOList);
            for (ProfileVO profileVO : profileVOSet) {
                redisTemplate.opsForSet().add("follower::" + userId, profileVO);
            }
        }
        return profileVOSet;
    }

    /**
     * 查看朋友列表
     * @param userId
     * @return
     */
    @Override
    public Set<ProfileVO> getFriendSet(Long userId) {
        if(!BaseContext.getCurrentId().equals(userId))
            throw new PermissionException(MessageConstant.FRIEND_NOT_VISIBLE);
        Set<ProfileVO> followerSet = getFollowerSet(userId);
        Set<ProfileVO> followingSet = getFollowingSet(userId);
        followingSet.retainAll(followerSet);
        return followingSet;
    }

    /**
     * 添加关注
     * @param userId
     */
    @Override
    public void follow(Long userId) {
        if(userId.equals(BaseContext.getCurrentId()))
            throw new FollowException(MessageConstant.FOLLOW_MYSELF);
        // 查看是否已经存在关系元组
        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<Relation>()
                .eq(Relation::getFollowId, userId)
                .eq(Relation::getCreateUser, BaseContext.getCurrentId());
        Relation relation = getOne(wrapper);
        if(relation != null){
            relation.setIsDelete(0);
        }
        else{
            relation = new Relation();
            relation.setCreateUser(BaseContext.getCurrentId());
            relation.setFollowId(userId);
        }
        relation.setCreateTime(LocalDateTime.now());
        saveOrUpdate(relation);

        // 添加用户的关注数 TODO 后续应该把关注数，粉丝数、获赞量这些常用信息保存在缓存中操作
        User user = userMapper.selectById(BaseContext.getCurrentId());
        user.setFollowing(user.getFollowing()+1);
        userMapper.updateById(user);

        // 添加用户的粉丝数
        user = userMapper.selectById(userId);
        user.setFollower(user.getFollower()+1);
        userMapper.updateById(user);

        // 清空受影响的缓存
        redisTemplate.delete("following::" + BaseContext.getCurrentId());
        redisTemplate.delete("follower::" + userId);
    }

    /**
     * 取消关注
     * @param userId
     */
    @Override
    public void unfollow(Long userId) {
        if(userId.equals(BaseContext.getCurrentId()))
            throw new FollowException(MessageConstant.UNFOLLOW_MYSELF);
        // 查看是否已经存在关系元组
        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<Relation>()
                .eq(Relation::getFollowId, userId)
                .eq(Relation::getCreateUser, BaseContext.getCurrentId());
        Relation relation = getOne(wrapper);
        if(relation != null){
            relation.setIsDelete(1);
            updateById(relation);

            // 减少用户的关注数 TODO 后续应该把关注数，粉丝数、获赞量这些常用信息保存在缓存中操作
            User user = userMapper.selectById(BaseContext.getCurrentId());
            user.setFollowing(user.getFollowing()-1);
            userMapper.updateById(user);

            // 减少用户的粉丝数
            user = userMapper.selectById(userId);
            user.setFollower(user.getFollower()-1);
            userMapper.updateById(user);

            // 清空受影响的缓存
            redisTemplate.delete("following::" + BaseContext.getCurrentId());
            redisTemplate.delete("follower::" + userId);
        }
    }

    /**
     * 获取并设置和当前用户的关系
     * @param profileVOSet
     */
    @Override
    public void setRelationWithCurrentUser(Set<ProfileVO> profileVOSet) {
        if(BaseContext.getCurrentId() == null){
            profileVOSet.forEach(profileVO -> {
                profileVO.setRelation(RelationConstant.PASSERBY);
            });
            return;
        }
        Set<ProfileVO> followingSet = getFollowingSet(BaseContext.getCurrentId());
        Set<ProfileVO> followerSet = getFollowerSet(BaseContext.getCurrentId());
        Set<ProfileVO> friendSet = getFriendSet(BaseContext.getCurrentId());
        profileVOSet.forEach(profileVO -> {
            if(friendSet.contains(profileVO))
                profileVO.setRelation(RelationConstant.FRIEND);
            else if(followingSet.contains(profileVO))
                profileVO.setRelation(RelationConstant.FOLLOWING);
            else if(followerSet.contains(profileVO))
                profileVO.setRelation(RelationConstant.FOLLOWER);
            else
                profileVO.setRelation(RelationConstant.PASSERBY);
        });
    }

    /**
     * 获取并设置和当前用户的关系
     * @param userVO
     */
    @Override
    public void setRelationWithCurrentUser(UserVO userVO) {
        if(BaseContext.getCurrentId() == null){
            userVO.setRelation(RelationConstant.PASSERBY);
            return;
        }
        // 获取当前用户的状态
        Set<ProfileVO> followingSet = getFollowingSet(BaseContext.getCurrentId());
        Set<ProfileVO> followerSet = getFollowerSet(BaseContext.getCurrentId());
        Set<ProfileVO> friendSet = getFriendSet(BaseContext.getCurrentId());
        ProfileVO profileVO = new ProfileVO();
        BeanUtil.copyProperties(userVO,profileVO);
        if(friendSet.contains(profileVO))
            profileVO.setRelation(RelationConstant.FRIEND);
        else if(followingSet.contains(profileVO))
            profileVO.setRelation(RelationConstant.FOLLOWING);
        else if(followerSet.contains(profileVO))
            profileVO.setRelation(RelationConstant.FOLLOWER);
        else
            profileVO.setRelation(RelationConstant.PASSERBY);
        userVO.setRelation(profileVO.getRelation());
    }
}




