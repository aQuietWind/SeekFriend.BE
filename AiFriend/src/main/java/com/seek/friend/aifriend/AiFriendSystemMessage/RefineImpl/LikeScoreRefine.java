package com.seek.friend.aifriend.AiFriendSystemMessage.RefineImpl;

import com.seek.friend.aifriend.AiFriendSystemMessage.RefineInterface;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class LikeScoreRefine implements RefineInterface {

    public String refine(AiFriendDTO aiFriend){
        return "用户给出你对用户的好感度分数:\""+aiFriend.getLikeScore()+"\".\n"+
                """
                该分数的含义是用户定义下,你在与该用户相识以后,你对他的好感度应该是怎么样的.
                该分数的上限为10分，从0分到10分,你自己定义一个好感度分数表,从非常讨厌到非常喜欢等等程度进行分段,同时可以小幅度的根据之前用户所给的信息
                与你的背景故事融合.
                你的背景故事可以一定程度下铺垫下为什么你会对该用户有这样的好感度.但也不要太夸张,别忘了,这只是你们第一次相识(除非用户明确交代了你的某些过去).
                """;
    }































}
