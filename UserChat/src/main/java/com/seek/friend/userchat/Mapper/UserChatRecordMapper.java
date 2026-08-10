package com.seek.friend.userchat.Mapper;

import com.seek.friend.serviceobject.UserChat.ChatRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserChatRecordMapper {
    public void insert(ChatRecordDTO record);
    public List<ChatRecordDTO> getList(int start, int need, long roomId,long userId);
    public boolean withdraw(long recordId,long userId);
}
