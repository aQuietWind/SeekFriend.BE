package com.seek.friend.aifriend.AiFriendSystemMessage.RefineImpl;

import com.seek.friend.aifriend.AiFriendSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class DescriptionRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "用户给你的人物介绍是:\""+aiFriend.getDescription()+"\".\n" +
                """
                你的背景故事最好要一定程度的参考一下该任务介绍,不应该大幅度的违背该人物介绍,但是可以小幅度的在人物介绍中加一些别的元素作为参照,然后写入你的背景故事中
                """;
    }































}
