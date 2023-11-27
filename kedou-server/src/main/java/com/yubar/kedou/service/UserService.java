package com.yubar.kedou.service;

import com.yubar.kedou.domain.dto.UserLoginDTO;
import com.yubar.kedou.domain.dto.UserSignDTO;
import com.yubar.kedou.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yubar.kedou.domain.vo.UserVO;

/**
* @author 兰豹基
* @description 针对表【user】的数据库操作Service
* @createDate 2023-11-24 16:47:28
*/
public interface UserService extends IService<User> {

    /**
     * 用户登陆
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 用户查询
     * @param userId
     * @return
     */
    UserVO getInfoById(Long userId);

    User sign(UserSignDTO userSignDTO);
}
