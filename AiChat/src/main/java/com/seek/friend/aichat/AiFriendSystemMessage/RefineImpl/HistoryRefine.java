package com.seek.friend.aichat.AiFriendSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiFriendSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class HistoryRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "你的背景故事:\""+aiFriend.getCharacterHistory()+"\".\n";
    }































}
