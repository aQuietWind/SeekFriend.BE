package com.seek.friend.userchat.Controller;

import com.seek.friend.serviceobject.Common.Result;
import com.seek.friend.serviceobject.UserChat.ChatRoomDTO;
import com.seek.friend.userchat.Enum.RequestPathEnum;
import com.seek.friend.userchat.Service.UserChatRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.Chat_Room)
public class UserChatRoomController {

    private final UserChatRoomService userChatRoomService;
    public UserChatRoomController(UserChatRoomService userChatRoomService) {
        this.userChatRoomService = userChatRoomService;
    }

    @GetMapping(RequestPathEnum.Chat_Room_List)
    public Result<List<ChatRoomDTO>> getList(int start, int need) {
        return Result.success(userChatRoomService.getList(start,need));
    }
}
