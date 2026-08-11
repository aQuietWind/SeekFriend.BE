package com.seek.friend.userchat.Controller;

import com.seek.friend.serviceobject.Common.Result;
import com.seek.friend.serviceobject.UserChat.ChatRecordDTO;
import com.seek.friend.userchat.Enum.RequestPathEnum;
import com.seek.friend.userchat.Service.UserChatRecordService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.Chat_Record)
public class UserChatRecordController {

    private final UserChatRecordService userChatRecordService;

    public UserChatRecordController(UserChatRecordService userChatRecordService) {
        this.userChatRecordService = userChatRecordService;
    }

    //插入聊天
    @PostMapping(RequestPathEnum.Chat_Record_Chat)
    public Result<Void> insert(String description, @RequestBody MultipartFile file, long roomId) throws MQBrokerException, RemotingException, UnsupportedEncodingException, InterruptedException, MQClientException {
        userChatRecordService.insert(description,file,roomId);
        return Result.success();
    }

    //查询聊天记录
    @GetMapping(RequestPathEnum.Chat_Record_List)
    public Result<List<ChatRecordDTO>> getList(int start, int need, long roomId){
        return Result.success(userChatRecordService.getList(start,need,roomId));
    }

    //撤回记录
    @PutMapping(RequestPathEnum.Chat_Record_Withdraw)
    public Result<Void> withdraw(long recordId){
        userChatRecordService.withdraw(recordId);
        return Result.success();
    }

}
