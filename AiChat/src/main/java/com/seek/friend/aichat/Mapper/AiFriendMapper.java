package com.seek.friend.aichat.Mapper;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiFriendMapper {
    public void insert(AiFriendDTO aiFriend);
    public AiFriendDTO getDetail(long aiFriendId);
}
