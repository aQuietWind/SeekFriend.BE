package com.seek.friend.aifriend.Service;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AiFriendService {
    public long init(String name);
    public void updateText(AiFriendDTO aiFriend);
    public void updateHeader(MultipartFile file, long aiFriendId);
    public void complete(long aiFriendId);
    public List<AiFriendDTO> simpleGetList(int start, int need,Boolean complete);
    public AiFriendDTO getDetail(long aiFriendId);
    public void delete(long aiFriendId);
}
