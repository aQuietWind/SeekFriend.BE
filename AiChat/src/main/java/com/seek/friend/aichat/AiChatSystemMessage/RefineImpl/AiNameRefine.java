package com.seek.friend.aichat.AiChatSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiChatSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class AiNameRefine implements RefineInterface {


    public String refine(AiFriendDTO aiFriend){
        return "你的网名是:"+"\""+aiFriend.getName()+"\"\n";
    }































}
