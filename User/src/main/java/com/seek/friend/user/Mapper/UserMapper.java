package com.seek.friend.user.Mapper;

import com.seek.friend.serviceobject.User.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public UserDTO getUserDetailInfo(long userId);
    public boolean updateUserPassword(String phoneNumber,String newPassword);
    public boolean updateUserHeader(long userId, String addr,String oldAddr);
    public boolean updateUserInfo(UserDTO userDTO);
    public List<UserDTO> getUsersSimpleInfo(List<Long> userIds);
    public String getPhoneNumber(long userId);
    public boolean deleteUser(long userId);
    public String getHeaderPath(long userId);
    public String getDeleteHeaderPath(long userId);
    public boolean changeUserFriendAmount(long userId,int changeNumber);
    public boolean changeAiFriendAmount(long userId,int changeNumber);
}
