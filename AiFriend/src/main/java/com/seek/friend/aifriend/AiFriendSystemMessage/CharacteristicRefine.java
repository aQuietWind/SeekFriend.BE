package com.seek.friend.aifriend.AiFriendSystemMessage;

import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;

public class CharacteristicRefine implements RefineInterface{

    public String refine(AiFriendDTO aiFriend){
        return "用户给出的人物个性特点:"+aiFriend.getCharacteristic()+".\n"+
                """
                你可以在自己的人物故事背景中给出你之所以有这样的个性特点的剧情,并且在此基础上串通上下文,使全文连贯.
                除此之外,你也可以给出一些其他的个性特点作为"隐藏"要素,这样可以增加用户对你的兴趣.
                """;
    }































}
