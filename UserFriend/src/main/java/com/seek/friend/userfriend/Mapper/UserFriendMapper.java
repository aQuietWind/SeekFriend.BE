package com.seek.friend.userfriend.Mapper;

import com.seek.friend.serviceobject.UserFriend.UserFriendConnectionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFriendMapper {
    public Long getConnectionIdByUser(long firstUserId,long secondUserId);
    public boolean applyFriend(long connectionId,long applicantUserId);
    public boolean insertFriendApplication(long connectionId,long applicantUserId,long respondentUserId);
    public List<UserFriendConnectionDTO> getApplicantList(int start, int need,long userId);
    public List<UserFriendConnectionDTO> getRespondentList(int start, int need,long userId);
    public List<UserFriendConnectionDTO> getFriendList(int start, int need,long userId);
    public long getApplicantUserId(long connectionId,long respondentUserId);
    public void respondApplication(long connectionId,boolean value,long respondentUserId);
    public void deleteFriend(long connectionId,long userId);
}
