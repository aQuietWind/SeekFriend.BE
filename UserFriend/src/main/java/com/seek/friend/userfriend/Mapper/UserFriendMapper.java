package com.seek.friend.userfriend.Mapper;

import com.seek.friend.serviceobject.UserFriend.UserFriendConnectionDTO;

import java.util.List;

public interface UserFriendMapper {

    public void applyFriend(long applicantUserId,long respondentUserId);
    public List<UserFriendConnectionDTO> getApplicantList(int start, int need,long userId);
    public List<UserFriendConnectionDTO> getRespondentList(int start, int need,long userId);
    public List<UserFriendConnectionDTO> getFriendList(int start, int need,long userId);
    public void respondApplication(long connectionId,Boolean value,long userId);
    public void deleteFriend(long connectionId,long userId);
}
