package com.seek.friend.aichat.Controller;

import com.seek.friend.aichat.Enum.RequestPathEnum;
import com.seek.friend.aichat.Service.ChatRecordService;
import com.seek.friend.serviceobject.AiChat.ChatRecordDTO;
import com.seek.friend.serviceobject.Common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(RequestPathEnum.Chat_Record)
@RestController
public class RecordController {

    private final ChatRecordService chatRecordService;

    @Autowired
    public RecordController(ChatRecordService chatRecordService) {
        this.chatRecordService = chatRecordService;
    }

    @PostMapping(RequestPathEnum.Chat_Record_Chat)
    public Result<String> chat( String description, MultipartFile file ,long aiFriendId){
        return Result.success(chatRecordService.chat(description,file,aiFriendId));
    }

    @GetMapping(RequestPathEnum.Chat_Record_Get_List)
    public Result<List<ChatRecordDTO>> getList(int start,int need,long aiFriendId){
        return Result.success(chatRecordService.getList(start,need,aiFriendId));
    }
}
