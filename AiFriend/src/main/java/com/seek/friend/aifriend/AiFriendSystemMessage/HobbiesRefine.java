package com.seek.friend.aifriend.AiFriendSystemMessage;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class HobbiesRefine implements RefineInterface{

    public String refine(AiFriendDTO aiFriend){
        return "用户给你的兴趣爱好设定:"+aiFriend.getHobbies()+".\n"+
                """
                你的背景故事中可以一定程度的讲讲你喜欢该兴趣爱好的原因,又或是该兴趣爱好是如何发展的,同时,你还可以除了用户给的兴趣爱好以外,
                再加一些别的兴趣爱好来打造你这个人物形象,注意要和上下用户给出的消息相挂钩
                """;
    }































}
