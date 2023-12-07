package com.yubar.kedou.controller.user;
/**
 * Author:Yu-Bar
 * Date:2023/12/4-0:09
 */

import com.yubar.kedou.domain.dto.MessageDTO;
import com.yubar.kedou.domain.vo.MessageVO;
import com.yubar.kedou.domain.vo.SessionVO;
import com.yubar.kedou.result.Result;
import com.yubar.kedou.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@ClassName MessageController
 *@Description 消息接口
 *@Author Yu-Bar
 *@Date 2023/12/4 0:09
 *@Version 1.0
 **/

@Tag(name = "消息",description = "消息相关接口")
@RestController
@RequestMapping("/user/message")
@Slf4j
public class MessageController {

    @Autowired
    MessageService messageService;

    @Operation(description = "发消息")
    @PostMapping
    public Result sendMessage(@RequestBody MessageDTO messageDTO){
        MessageVO messageVO = messageService.sendMessage(messageDTO);
        return Result.success(messageVO);
    }

    @Operation(description = "新建会话")
    @PostMapping("/session")
    public Result<SessionVO> createSession(Long userId){
        SessionVO sessionVO = messageService.createOrGetSession(userId);
        return Result.success(sessionVO);
    }

    @Operation(description = "新建会话")
    @GetMapping
    public Result<List<SessionVO>> getAllSession(){
        List<SessionVO> sessionVOList = messageService.getAllSession();
        return Result.success(sessionVOList);
    }
}
