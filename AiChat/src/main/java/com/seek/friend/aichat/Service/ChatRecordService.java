package com.seek.friend.aichat.Service;

import com.seek.friend.serviceobject.AiChat.ChatRecordDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChatRecordService {
    public String chat(String description, MultipartFile file,long aiFriendId);
    public List<ChatRecordDTO> getList(int start, int need,long aiFriendId);
}
