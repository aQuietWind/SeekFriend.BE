package com.seek.friend.aichat.Mapper;

import com.seek.friend.serviceobject.AiChat.ChatRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    public void insert(ChatRecordDTO record);
    public void insertMany(List<ChatRecordDTO> records);
    public List<ChatRecordDTO> getList(int start,int need,long aiFriendId,long userId);
}
