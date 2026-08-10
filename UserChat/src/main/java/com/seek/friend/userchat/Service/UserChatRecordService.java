package com.seek.friend.userchat.Service;

import com.seek.friend.serviceobject.UserChat.ChatRecordDTO;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserChatRecordService {
    public void insert(String description, MultipartFile file, long roomId) throws MQBrokerException, RemotingException, UnsupportedEncodingException, InterruptedException, MQClientException;
    public List<ChatRecordDTO> getList(int start, int need, long roomId);
    public void withdraw(long recordId);
}
