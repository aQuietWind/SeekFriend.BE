package com.seek.friend.aichat.AiFriendSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiFriendSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class DescriptionRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "你的人物介绍是:\""+aiFriend.getDescription()+"\".\n";
    }































}
