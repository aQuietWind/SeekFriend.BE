package com.seek.friend.userfriend.Service;

import com.seek.friend.serviceobject.UserFriend.UserFriendConnectionDTO;

import java.util.List;

public interface UserFriendService {
    public void applyFriend(long userId);
    public List<UserFriendConnectionDTO> getApplicantList(int start, int need);
    public List<UserFriendConnectionDTO> getRespondentList(int start, int need);
    public List<UserFriendConnectionDTO> getFriendList(int start, int need);
    public void respondApplication(long connectionId,boolean value);
    public void deleteFriend(long connectionId);
}
