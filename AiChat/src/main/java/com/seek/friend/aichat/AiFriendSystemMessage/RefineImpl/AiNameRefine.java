package com.seek.friend.aichat.AiFriendSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiFriendSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class AiNameRefine implements RefineInterface {


    public String refine(AiFriendDTO aiFriend){
        return "你的网名是:"+"\""+aiFriend.getName()+"\"\n";
    }































}
