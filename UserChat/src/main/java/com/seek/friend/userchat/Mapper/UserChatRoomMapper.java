package com.seek.friend.userchat.Mapper;

import com.seek.friend.serviceobject.UserChat.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserChatRoomMapper {
    public List<ChatRoomDTO> getList(int start, int need,long userId);
    public boolean updateAbleChat(long connectionId, boolean value, int version);
    public boolean insertChatRoom(long roomId,long connectionId,long firstUserId,long secondUserId);
    public boolean syncLastestChatTime(long chatRoomId);
}
