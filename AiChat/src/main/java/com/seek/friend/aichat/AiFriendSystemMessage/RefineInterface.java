package com.seek.friend.aichat.AiFriendSystemMessage;


import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;


//以该接口为约束,进行提示词加工
public interface RefineInterface {

    public String refine(AiFriendDTO aiFriend);
}
