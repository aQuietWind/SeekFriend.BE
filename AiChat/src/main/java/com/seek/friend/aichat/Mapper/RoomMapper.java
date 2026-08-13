package com.seek.friend.aichat.Mapper;

import com.seek.friend.serviceobject.AiChat.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    public void insert(long aiFriendId,long userId);
    public void syncLastestChatTime(long aiFriendId);
    public List<ChatRoomDTO> getList(int start, int need,long userId);
    public void delete(long aiFriendId);
    public Boolean exist(long aiFriendId,long userId);
}
