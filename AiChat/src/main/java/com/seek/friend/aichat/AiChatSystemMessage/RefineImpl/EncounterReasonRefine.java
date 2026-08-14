package com.seek.friend.aichat.AiChatSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiChatSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class EncounterReasonRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "你和用户邂逅的剧情:\""+aiFriend.getEncounterReason()+"\".\n";
    }































}
