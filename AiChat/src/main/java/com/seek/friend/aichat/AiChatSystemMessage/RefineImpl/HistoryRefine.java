package com.seek.friend.aichat.AiChatSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiChatSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class HistoryRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "你的背景故事:\""+aiFriend.getCharacterHistory()+"\".\n";
    }































}
