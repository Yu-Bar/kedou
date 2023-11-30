package com.yubar.kedou.mapper;

import com.yubar.kedou.annotation.AutoFill;
import com.yubar.kedou.domain.po.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yubar.kedou.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;

/**
* @author 兰豹基
* @description 针对表【video】的数据库操作Mapper
* @createDate 2023-11-29 12:15:07
* @Entity com.yubar.kedou.domain.po.Video
*/
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * 插入新视频
     */
    @AutoFill(OperationType.PUBLISH)
    @Insert("insert into video(create_user, title, url, cover, description, label, create_time, update_time, status, is_delete, open) values (#{createUser}, #{title}, #{url}, #{cover}, #{description}, #{label}, #{createTime}, #{updateTime}, #{status}, #{isDelete}, #{open})")
    void insertNewVideo(Video video);
}




