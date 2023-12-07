package com.yubar.kedou.event;
/**
 * Author:Yu-Bar
 * Date:2023/12/5-19:49
 */

import com.yubar.kedou.domain.dto.MessageMQDTO;
import com.yubar.kedou.domain.po.Message;
import org.springframework.context.ApplicationEvent;

/**
 *@ClassName MessageReceiveEvent
 *@Description 接收消息事件
 *@Author Yu-Bar
 *@Date 2023/12/5 19:49
 *@Version 1.0
 **/
public class MessageReceiveEvent extends ApplicationEvent {
    public MessageReceiveEvent(MessageMQDTO source) {
        super(source);
    }
}
