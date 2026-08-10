package com.seek.friend.userchat.Mapper;

import com.seek.friend.serviceobject.UserChat.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserChatRoomMapper {
    public List<ChatRoomDTO> getList(int start, int need,long userId);
    public boolean updateState(long firstUserId,long secondUserId,boolean value);
    public boolean insertChatRoom(long firstUserId,long secondUserId);
}
