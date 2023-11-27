package com.yubar.kedou.mapper;

import com.yubar.kedou.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
* @author 兰豹基
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-11-27 16:07:01
* @Entity com.yubar.kedou.domain.po.User
*/
public interface UserMapper extends BaseMapper<User> {

    /**
     * 插入用户，返回自增主键
     * @param user
     * @return
     */
    @Insert("INSERT INTO user (username, nickname, password, bio, profile) VALUES (#{username}, #{nickname}, #{password}, #{bio}, #{profile})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

}




