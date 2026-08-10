package com.seek.friend.userfriend.Mapper;

import com.seek.friend.serviceobject.UserFriend.ChatConnectionMQDTO;
import com.seek.friend.serviceobject.UserFriend.UserFriendConnectionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFriendMapper {
    public Long getConnectionIdByUser(long firstUserId,long secondUserId);
    public boolean applyFriend(long connectionId,long applicantUserId,long respondentUserId);
    public boolean insertFriendApplication(long connectionId,long applicantUserId,long respondentUserId);
    public List<UserFriendConnectionDTO> getApplicantList(int start, int need,long userId);
    public List<UserFriendConnectionDTO> getRespondentList(int start, int need,long userId);
    public List<UserFriendConnectionDTO> getFriendList(int start, int need,long userId);
    public ChatConnectionMQDTO respondApplication(long connectionId, boolean value, long respondentUserId);
    public ChatConnectionMQDTO deleteFriend(long connectionId,long userId);
}
