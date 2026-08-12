package com.seek.friend.aifriend.Mapper;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AiFriendMapper {
    public void init(long aiFriendId,String name,long userId);
    public boolean updateText(AiFriendDTO aiFriend);
    public List<String> updateHeader(String addr,long aiFriendId,long userId);
    public boolean complete(String history,long aiFriendId,long userId);
    public List<AiFriendDTO> simpleGetList(int start, int need, long userId,Boolean complete);
    public AiFriendDTO getDetail(long aiFriendId,long userId);
    public Boolean delete(long aiFriendId,long userId);
}
