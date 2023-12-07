package com.yubar.kedou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.domain.po.Session;
import com.yubar.kedou.service.SessionService;
import com.yubar.kedou.mapper.SessionMapper;
import org.springframework.stereotype.Service;

/**
* @author 兰豹基
* @description 针对表【session】的数据库操作Service实现
* @createDate 2023-12-04 20:39:33
*/
@Service
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session>
    implements SessionService{

}




