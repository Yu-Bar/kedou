package com.yubar.kedou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.domain.po.Message;
import com.yubar.kedou.service.MessageService;
import com.yubar.kedou.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 兰豹基
* @description 针对表【message】的数据库操作Service实现
* @createDate 2023-11-24 16:47:28
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




