package com.seek.friend.aichat.AiChatSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiChatSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class HobbiesRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "你的兴趣爱好设定:\""+aiFriend.getHobbies()+"\".\n";
    }































}
