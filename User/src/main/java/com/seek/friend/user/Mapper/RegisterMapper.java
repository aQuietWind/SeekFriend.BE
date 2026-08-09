package com.seek.friend.user.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    public void insertUser(long userId, String phoneNumber, String password);
}
