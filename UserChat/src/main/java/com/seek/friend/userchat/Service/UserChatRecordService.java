package com.seek.friend.userchat.Service;

import com.seek.friend.serviceobject.UserChat.ChatRecordDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserChatRecordService {
    public void insert(String description, MultipartFile file, long roomId);
    public List<ChatRecordDTO> getList(int start, int need, long roomId);
    public void withdraw(long recordId);
}
