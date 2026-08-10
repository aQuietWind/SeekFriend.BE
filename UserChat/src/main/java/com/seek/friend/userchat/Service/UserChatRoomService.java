package com.seek.friend.userchat.Service;

import com.seek.friend.serviceobject.UserChat.ChatRoomDTO;

import java.util.List;

public interface UserChatRoomService {
    public List<ChatRoomDTO> getList(int start,int need);
}
