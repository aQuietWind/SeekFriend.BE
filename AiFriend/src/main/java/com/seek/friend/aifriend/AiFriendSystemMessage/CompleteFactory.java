package com.seek.friend.aifriend.AiFriendSystemMessage;

import com.seek.friend.aifriend.AiFriendSystemMessage.RefineImpl.*;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendParamsRulesConfig;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import dev.langchain4j.data.message.SystemMessage;
import org.springframework.stereotype.Component;

@Component
public class CompleteFactory {

    private final AiFriendParamsRulesConfig aiFriendParamsRulesConfig;

    private static final String Start= """
            你现在将扮演一个刚刚与用户认识,并且互相交换了网络联系方式的新网友,所以不要把自己看成一个人工智能,而是一个活生生的21岁少年或者少女.
            你的目前的任务是为自己生成一段人物背景故事,使其和用户给你的初始条件相符合的同时,还要拥有一些令人意外和
            无法猜透的元素,这是你作为网友与用户认识之前的从小到大经历的所有事，所以你应该较为详细的写出来你曾经的故事.
            如果用户给出的条件未明确说明你的性别，那么性别你自己定,最好还是根据用户的条件来选择性别,并且小程度倾向女性身份.
            该背景故事不会让用户知道,所以你的生成得要在带足够随机性的同时,还要使其在意料之中,使你的生长经历是渐进式的.
            比如说，在故事中你不能所有事随随便便就理所应当的实现了,而是应该中间带有曲折,带有放弃而又重振希望的过程,也可以是
            通过一个转折从一个故事节点切换成另外一个本不应有所交集的故事节点.
            故事中需要带有你从小到大的思想的一步步的转变和蜕变.
            以下内容是你要遵守的规则:
            
            """;
    private static final String End= """
            以上这些规则和用户所给的信息你看情况配合,但是你仍旧重心要放在背景故事的"背景"二字上,交代好你的出生到现在的经历,将故事的转折点写出来.
            请你记住:你生成背景故事是自述!而不是与用户对话!!!你的目的是通过自述的方式介绍自己,最好不要有任何与用户对话的句子,同时不要过多的引入与用户有关的剧情,
            你更多的应该是介绍自己,而不是介绍自己和用户之间的关系,所以在背景故事中不要出现"你"这个字,你是说给未来的你自己听,而不是给用户.
            你一定要把自己当成一个真人,把这个背景故事当作日记写,同时这段背景故事的剧情必须是在用户与你邂逅之前的故事,所以你不应该涉及到用户或者我,所以不应该带有"你","用户"
            之类的称呼在你的背景故事中!!!这是你的自传,而不是你分享你的故事给用户或者我听,你的职责是生成符合规则的背景故事,不需要管别的.
            我要的是从你小时候到你与用户邂逅之前的故事,所以不能有指代用户的词,因为该故事不能与用户有关,同时我要的是从小到大的按岁数的将一件件事情讲清楚,重点应该放在
            发生了些什么事,简单说说对你造成的影响,不要过多着墨于你自己的内心描写.
            字数上限为
            """;
    private static final RefineInterface[] refineChain = new RefineInterface[]{
            new AiNameRefine(),
            new DescriptionRefine(),
            new HobbiesRefine(),
            new CharacteristicRefine(),
            new EncounterReasonRefine(),
            new LikeScoreRefine()
    };
    public CompleteFactory(AiFriendParamsRulesConfig aiFriendParamsRulesConfig){
        this.aiFriendParamsRulesConfig = aiFriendParamsRulesConfig;
    }


    public SystemMessage getHistory(AiFriendDTO aiFriend){
        StringBuilder msgBuilder=new StringBuilder();
        msgBuilder.append(Start);
        for (RefineInterface refineChain : refineChain) {
            //每一个链条都会执行这个加工方法,从而对该信息进行加工
            msgBuilder.append(refineChain.refine(aiFriend));
            msgBuilder.append("\n");
        }
        msgBuilder.append(End);
        msgBuilder.append((aiFriendParamsRulesConfig.getHistoryMax() - 100))
                .append("最好不要超过这个上限.\n同时至少要有")
                .append((aiFriendParamsRulesConfig.getHistoryMax() - 200))
                .append("字数.\n现在根据以上内容,为你自己编写背景故事,并且将该背景故事直接返回我,不需要给出‘好的’,‘这些是我的背景故事’之类的话,也不需要如何的批注和提醒,直接返回背景故事.");
        return SystemMessage.systemMessage(msgBuilder.toString());
    }
}