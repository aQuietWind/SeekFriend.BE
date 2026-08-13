package com.seek.friend.aichat.Service;

import com.seek.friend.serviceobject.AiChat.ChatRoomDTO;

import java.util.List;

public interface ChatRoomService {
    public List<ChatRoomDTO> getList(int start, int need);
}
