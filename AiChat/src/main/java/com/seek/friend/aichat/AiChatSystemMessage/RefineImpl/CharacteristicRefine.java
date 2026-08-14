package com.seek.friend.aichat.AiChatSystemMessage.RefineImpl;

import com.seek.friend.aichat.AiChatSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class CharacteristicRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "你的人物个性特点:\""+aiFriend.getCharacteristic()+"\".\n";
    }































}
