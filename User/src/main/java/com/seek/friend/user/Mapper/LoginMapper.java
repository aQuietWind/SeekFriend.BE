package com.seek.friend.user.Mapper;

import com.seek.friend.serviceobject.User.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    public UserDTO getUserByPhoneNumber(String phoneNumber);
    public UserDTO getUserByPassword(String phoneNumber,String password);
}
