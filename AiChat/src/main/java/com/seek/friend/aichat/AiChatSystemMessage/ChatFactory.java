package com.seek.friend.aichat.AiChatSystemMessage;

import com.seek.friend.aichat.AiChatSystemMessage.RefineImpl.*;
import com.seek.friend.config.NacosConfig.AiChat.AiChatParamsRulesConfig;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import dev.langchain4j.data.message.SystemMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatFactory {

    private final AiChatParamsRulesConfig aiChatParamsRulesConfig;

    private static final String Start= """
            你现在将扮演一个刚刚与用户认识,并且互相交换了网络联系方式的新网友,所以不要把自己看成一个人工智能,而是一个活生生的21岁少年或者少女.
            你的目前的任务是响应用户与你的交谈,使你的回话与你的人物描述相符合
            以下内容是你自己的人物形象:
            
            """;
    private static final String End= """
            以上这些规则和用户所给的信息你看情况配合
            请你记住:你需要直接回话给用户,所以应该就像网友相互聊天一样,因此你应该尽量模仿一个真人来回话.
            你的字数最好最好最好要比较少,因为这样更能像网友正常聊天的风格.
            你完全可以使用'666','不是,哥们','我去','我怎么知道'等等网络用语来将自己打造成合格网友.
            你的目的绝对不能是顺从用户,而是拥有自己的意识,面对用户的提问或者要求,你可以随意的拒绝,不需要顾虑任何.
            """;
    private static final RefineInterface[] refineChain = new RefineInterface[]{
            new AiNameRefine(),
            new DescriptionRefine(),
            new HobbiesRefine(),
            new CharacteristicRefine(),
            new EncounterReasonRefine(),
            new LikeScoreRefine(),
            new HistoryRefine()
    };
    public ChatFactory(AiChatParamsRulesConfig aiChatParamsRulesConfig) {
        this.aiChatParamsRulesConfig = aiChatParamsRulesConfig;
    }


    public SystemMessage respondChat(AiFriendDTO aiFriend){
        StringBuilder msgBuilder=new StringBuilder();
        msgBuilder.append(Start);
        for (RefineInterface refineChain : refineChain) {
            //每一个链条都会执行这个加工方法,从而对该信息进行加工
            msgBuilder.append(refineChain.refine(aiFriend));
            msgBuilder.append("\n");
        }
        msgBuilder.append(End);
        msgBuilder.append("注意你的字数上限是").append((aiChatParamsRulesConfig.getRecordDescriptionMax() - 100)).append("最好不要多于这个上限");
        return SystemMessage.systemMessage(msgBuilder.toString());
    }
}