package com.seek.friend.aichat.Mapper;

import com.seek.friend.serviceobject.AiChat.ChatRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    public void insert(ChatRecordDTO chatRecordDTO);
    public List<ChatRecordDTO> getList(int start,int end,long aiFriendId,long userId);
}
