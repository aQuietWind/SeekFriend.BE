package com.seek.friend.aifriend.Service;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AiFriendService {
    public long initText(AiFriendDTO aiFriend);
    public void initHeader(MultipartFile file, long aiFriendId);
    public List<AiFriendDTO> simpleGetList(int start, int need);
    public AiFriendDTO getDetail(long aiFriendId);
    public void delete(long aiFriendId);
}
