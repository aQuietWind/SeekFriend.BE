package com.seek.friend.aifriend.AiFriendSystemMessage.RefineImpl;

import com.seek.friend.aifriend.AiFriendSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class AiNameRefine implements RefineInterface {


    public String refine(AiFriendDTO aiFriend){
        return " 用户给你的网名是:"+"\""+aiFriend.getName()+"\"\n"+
                """
                你应该让你的背景故事尽量符合该名称,如果可以带上为什么使用该网民的原因也是可以的.
                如果该网名实在无法接受,则可以忽略该网名.记住,这只是一个网名,而非你的真实姓名
                """;
    }































}
