package com.seek.friend.aichat.Service.Impl;

import com.seek.friend.aichat.AiFriendSystemMessage.ChatFactory;
import com.seek.friend.aichat.Caffeine.AiFriendCaffeine;
import com.seek.friend.aichat.Mapper.AiFriendMapper;
import com.seek.friend.aichat.Mapper.RecordMapper;
import com.seek.friend.aichat.Mapper.RoomMapper;
import com.seek.friend.aichat.Service.ChatRecordService;
import com.seek.friend.config.NacosConfig.AiChat.AiChatParamsRulesConfig;
import com.seek.friend.config.NacosConfig.AiChat.AiChatRedisKeyConfig;
import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.serviceobject.AiChat.ChatRecordDTO;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.FileUtil.FileSave;
import com.seek.friend.util.Redis.RedisUtil;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RefreshScope
@Slf4j
@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    private static final String record="";

    private final AiFriendCaffeine aiFriendCaffeine;
    private final AiFriendMapper aiFriendMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RedisUtil redisUtil;
    private final AiChatRedisKeyConfig aiChatRedisKeyConfig;
    private final RecordMapper recordMapper;
    private final RoomMapper roomMapper;
    private final AiChatParamsRulesConfig aiChatParamsRulesConfig;
    private final ChatModel chatModel;
    private final IdUtil idUtil;
    private final ChatFactory chatFactory;

    @Autowired
    public ChatRecordServiceImpl(AiFriendCaffeine aiFriendCaffeine , AiFriendMapper aiFriendMapper , CommonParamRulesConfig commonParamRulesConfig
    , RedisUtil redisUtil , AiChatRedisKeyConfig aiChatRedisKeyConfig, RecordMapper recordMapper, RoomMapper roomMapper
    , AiChatParamsRulesConfig aiChatParamsRulesConfig, ChatModel chatModel , IdUtil idUtil, ChatFactory chatFactory) {
        this.aiFriendCaffeine = aiFriendCaffeine;
        this.aiFriendMapper = aiFriendMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.redisUtil = redisUtil;
        this.aiChatRedisKeyConfig = aiChatRedisKeyConfig;
        this.recordMapper = recordMapper;
        this.roomMapper = roomMapper;
        this.aiChatParamsRulesConfig = aiChatParamsRulesConfig;
        this.chatModel = chatModel;
        this.idUtil = idUtil;
        this.chatFactory = chatFactory;
    }

    @PostConstruct
    public void init(){
        redisUtil.trySetString(aiChatRedisKeyConfig.getRecordIdCount(),null,""+commonParamRulesConfig.getIdCapacity());
        FileSave.createDestDir(aiChatParamsRulesConfig.getRecordFileDest());
    }



    @Override
    public String chat(String description, MultipartFile file, long aiFriendId){
        //校验参数格式
        aiChatParamsRulesConfig.recordDescriptionCheck(description);
        commonParamRulesConfig.commonIdCheck(aiFriendId);
        //同时允许图片与音频一同出现
        String addr=null;
        if(file!=null&&!file.isEmpty())addr=FileSave.checkFile(file,commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageMusicType());
        long userId=TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        //查冷却,防脚本
        redisUtil.checkCooldown(aiChatRedisKeyConfig.getRecordInsertCooldown(),userId);
        //判断是否符合条件
        if (!roomMapper.exist(aiFriendId,userId))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //获取
        AiFriendDTO aiFriend= quickGetAiFriend(aiFriendId,userId);
        //保存文件
        if(addr!=null)FileSave.saveFile(file,Paths.get(aiChatParamsRulesConfig.getRecordFileDest(),addr));
        //获取会话记忆
        List<ChatMessage> stores=quickGetChatMessagesByRecords(chatFactory.respondChat(aiFriend),aiFriendId,userId);
        //构建Record
        ChatRecordDTO recordNow=new ChatRecordDTO(null,aiFriendId,userId,description,addr,null,false);
        //将本次会话添加进去
        stores.add(quickGetUserMessageByRecord(recordNow));
        //发起聊天
        String result=chatModel.chat(stores).aiMessage().text();
        //写入DB
        recordNow.setRecordId(idUtil.IdGenerateByIncrease(aiChatRedisKeyConfig.getRecordIdCount()));
        ChatRecordDTO aiRecord=new ChatRecordDTO(idUtil.IdGenerateByIncrease(aiChatRedisKeyConfig.getRecordIdCount()),aiFriendId,userId,result,null,null,true);
        recordMapper.insertMany(List.of(recordNow,aiRecord));
        return result;
    }

    //前端主动希望ai挑起话题
    @Override
    public String initiativeChat(long aiFriendId){
        return "";
    }

    @Override
    public List<ChatRecordDTO> getList(int start, int need, long aiFriendId){
        commonParamRulesConfig.commonIdCheck(aiFriendId);
        commonParamRulesConfig.needNumberCheck(need);
        long userId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(aiChatRedisKeyConfig.getRecordGetListCooldown(), userId);
        return recordMapper.getList(start,need,aiFriendId,userId);
    }






    private List<ChatMessage> quickGetChatMessagesByRecords(SystemMessage systemMessage,long aiFriendId,long userId){
        List<ChatRecordDTO> records=recordMapper.getList(0,aiChatParamsRulesConfig.getRecordStoreMax(),aiFriendId,userId);
        List<ChatMessage> messages= new ArrayList<>();
        //先添加系统信息
        if (systemMessage!=null&&!systemMessage.text().isEmpty())messages.add(systemMessage);
        if (records==null||records.isEmpty())return messages;
        //挨个根据类型添加信息
        for (int i = records.size(); i > 0; i--) {
            ChatRecordDTO record=records.get(i-1);
            if (record.getAi())messages.add(AiMessage.from(record.getDescription()));
            else messages.add(quickGetUserMessageByRecord(record));
        }
        return messages;
    }


    private AiFriendDTO quickGetAiFriend(long aiFriendId,long userId){
        //ai好友获取
        AiFriendDTO aiFriend=aiFriendCaffeine.getAndAutoLoad(aiFriendId,k->aiFriendMapper.getDetail(aiFriendId));
        if (aiFriend==null||aiFriend.getUserId()!=userId)throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        return aiFriend;
    }

    private UserMessage quickGetUserMessageByRecord(ChatRecordDTO record){
        if (record.getFileAddr()==null||record.getFileAddr().isEmpty())return UserMessage.from(TextContent.from(record.getDescription()));
        //提取文件为上下文
        ImageContent imageContent=ImageContent.from(Paths.get(aiChatParamsRulesConfig.getRecordFileDest(),record.getFileAddr()).toUri());
        return UserMessage.from(TextContent.from(record.getDescription()),imageContent);
    }

}
