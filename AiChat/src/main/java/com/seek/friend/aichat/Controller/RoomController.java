package com.seek.friend.aichat.Controller;

import com.seek.friend.aichat.Enum.RequestPathEnum;
import com.seek.friend.aichat.Service.ChatRoomService;
import com.seek.friend.serviceobject.AiChat.ChatRoomDTO;
import com.seek.friend.serviceobject.Common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.Chat_Room)
public class RoomController {

    private final ChatRoomService chatRoomService;

    @Autowired
    public RoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping(RequestPathEnum.Chat_Room_List)
    public Result<List<ChatRoomDTO>> getList(int start, int need){
        return Result.success(chatRoomService.getList(start,need));
    }
}
