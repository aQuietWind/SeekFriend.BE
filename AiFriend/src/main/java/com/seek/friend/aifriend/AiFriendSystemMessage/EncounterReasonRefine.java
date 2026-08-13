package com.seek.friend.aifriend.AiFriendSystemMessage;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class EncounterReasonRefine implements RefineInterface{

    public String refine(AiFriendDTO aiFriend){
        return "用户给出你们邂逅和互相添加的剧情:\""+aiFriend.getEncounterReason()+"\".\n"+
                """
                你可以看一看这段情节,学习一下用户眼中所希望你该有的性格特征,以及语言特色.同时背景故事要为该段邂逅剧情作一定小尺度的铺垫,
                当然,也不要过多着重于迎合这段邂逅剧情,你可以尽情的发挥创造力.
                """;
    }































}
