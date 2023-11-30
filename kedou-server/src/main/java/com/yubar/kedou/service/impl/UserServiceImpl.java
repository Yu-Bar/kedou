package com.yubar.kedou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.constant.StatusConstant;
import com.yubar.kedou.domain.dto.UserSignDTO;
import com.yubar.kedou.domain.po.Video;
import com.yubar.kedou.domain.vo.UserVO;
import com.yubar.kedou.domain.vo.VideoVO;
import com.yubar.kedou.exception.AccountExsistException;
import com.yubar.kedou.exception.AccountLockedException;
import com.yubar.kedou.exception.AccountNotFoundException;
import com.yubar.kedou.exception.PasswordErrorException;
import com.yubar.kedou.domain.dto.UserLoginDTO;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.mapper.VideoMapper;
import com.yubar.kedou.service.UserService;
import com.yubar.kedou.mapper.UserMapper;
import com.yubar.kedou.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-11-24 16:47:28
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Value("${kedou.profile}")
    private  String defultProfile;

    /**
     * 用户登陆
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        User user = getOne(wrapper.eq(User::getUsername, account));
        if(user == null)
            user = getOne(wrapper.eq(User::getPhone, account));

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info("登陆密码：{}",password);
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (user.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }


    /**
     * 用户查询
     * @param userId
     * @return
     */
    @Override
    public UserVO getInfoById(Long userId) {
        // 查询用户
        User user = getById(userId);
        if(user == null)
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        // 查询用户发布视频
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        // 按发布时间倒序排列
        wrapper.eq(Video::getCreateUser, userId)
                .orderByDesc(Video::getCreateTime);
        List<Video> videoList = videoMapper.selectList(wrapper);
        List<VideoVO> videoVOList = BeanUtil.copyToList(videoList, VideoVO.class);
        videoVOList.forEach(videoVO -> {
            videoVO.setCreateUser(user.getId());
            videoVO.setProfile(user.getProfile());
            videoVO.setNickname(user.getNickname());
        });
        userVO.setVideoList(videoVOList);
        return userVO;
    }

    @Override
    public User sign(UserSignDTO userSignDTO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        List<User> list = list(wrapper.eq(User::getUsername, userSignDTO.getAccount()));
        if(!list.isEmpty())
            throw new AccountExsistException(MessageConstant.ACCOUNT_ALREADY_EXIST);
        User user = new User();
        user.setUsername(userSignDTO.getAccount());
        user.setNickname("新科抖");
        user.setPassword(DigestUtils.md5DigestAsHex(userSignDTO.getPassword().getBytes()));
        user.setBio("这小汁懒得很，什么都没有写");
        System.out.println("defultProfile" + defultProfile);
        user.setProfile(defultProfile);
        userMapper.insertUser(user);
        return user;
    }
}




