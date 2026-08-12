package com.seek.friend.aifriend.Mapper;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AiFriendMapper {
    public void initText(AiFriendDTO aiFriend);
    public boolean initHeader(String addr,String history,long aiFriendId,long userId);
    public List<AiFriendDTO> simpleGetList(int start, int need, long userId,Boolean complete);
    public AiFriendDTO getDetail(long aiFriendId,long userId);
    public boolean delete(long aiFriendId,long userId);
}
