package com.yubar.kedou.mapper;

import com.yubar.kedou.annotation.AutoFill;
import com.yubar.kedou.domain.po.Session;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yubar.kedou.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
* @author 兰豹基
* @description 针对表【session】的数据库操作Mapper
* @createDate 2023-12-04 20:39:33
* @Entity com.yubar.kedou.domain.po.Session
*/
public interface SessionMapper extends BaseMapper<Session> {

    @AutoFill(OperationType.CREATE)
    @Insert("insert into session(user_a, user_b, create_time, update_time) values (#{userA}, #{userB}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insetNewSession(Session session);
}




