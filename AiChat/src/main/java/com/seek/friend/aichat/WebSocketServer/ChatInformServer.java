package com.seek.friend.aichat.WebSocketServer;

import com.seek.friend.aichat.Mapper.RoomMapper;
import com.seek.friend.config.NacosConfig.AiChat.AiChatRedisKeyConfig;
import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.Common.JWTConfig;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatInformServer implements WebSocketHandler {

    private static final String Ai_Friend_Id = "aiFriendId";
    private static final String User_Id = "userId";

    // 存放所有在线会话
    private static final ConcurrentHashMap<Long, ConcurrentHashMap<Long, WebSocketSession>> Session_Map = new ConcurrentHashMap<>();
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RedisUtil redisUtil;
    private final RoomMapper roomMapper;
    private final JWTConfig jwtConfig;
    private final AiChatRedisKeyConfig aiChatRedisKeyConfig;

    @Autowired
    public ChatInformServer(CommonParamRulesConfig commonParamRulesConfig,RoomMapper roomMapper
    ,  RedisUtil redisUtil, JWTConfig jwtConfig, AiChatRedisKeyConfig aiChatRedisKeyConfig) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.redisUtil = redisUtil;
        this.jwtConfig = jwtConfig;
        this.roomMapper = roomMapper;
        this.aiChatRedisKeyConfig = aiChatRedisKeyConfig;
    }

    // 连接建立成功
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception{
        Long aiFriendId= quickGetAiFriendIdFirst(session);
        //检查该请求参数
        commonParamRulesConfig.commonIdCheck(aiFriendId);
        //获取该请求账户的Id
        long userId=quickGetIdAndCheckCooldown(session);
        //检查该账户是否有权限监听该合格聊天室
        if (!roomMapper.exist(aiFriendId, userId))throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        //放置该session
        quickSaveSession(session,aiFriendId,userId);
    }

    // 连接关闭
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) throws Exception {
        //删除该会话
        quickRemoveSession(session);
    }

    //异常回调
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("websocket会话异常,session为:{}",session,exception);
    }
    @Override
    public boolean supportsPartialMessages() {return false;}
    /** 收到消息时的回调 */
    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {}


    //对指定的聊天室id广播消息
    public void broadcastRoomId(long aiFriendId,String msg) throws IOException {
        ConcurrentHashMap<Long,WebSocketSession> map=Session_Map.get(aiFriendId);
        if (map==null)return;
        for (Map.Entry<Long, WebSocketSession> entry : map.entrySet()) {
            //上锁, 因为这个冲突会报错
            synchronized (entry.getValue().getId()) {
                //发送消息
                if (entry.getValue().isOpen()) entry.getValue().sendMessage(new TextMessage(msg));
            }
        }
    }

    private long quickGetIdAndCheckCooldown(WebSocketSession session) {
        long userId= Long.parseLong(session.getHandshakeHeaders().getFirst(jwtConfig.getGlobal().getRequestHeaderTokenIdName()));
        redisUtil.checkCooldown(aiChatRedisKeyConfig.getRoomInformListenCooldown(),userId);
        return userId;
    }

    private void quickRemoveSession(WebSocketSession session){
        Object roomId= session.getAttributes().get(Ai_Friend_Id);
        if (roomId!=null)Session_Map.get((Long) roomId).remove( (Long) session.getAttributes().get(User_Id),session);
    }

    private void quickSaveSession(WebSocketSession session,long aiFriendId,long userId) throws IOException {
        //先放置，方便后续拿取
        session.getAttributes().put(Ai_Friend_Id, aiFriendId);
        session.getAttributes().put(User_Id, userId);
        Session_Map.computeIfAbsent(aiFriendId, k -> new ConcurrentHashMap<>());
        if (Session_Map.get(aiFriendId).putIfAbsent(userId,session)!=null)session.close();
    }



    private Long quickGetAiFriendIdFirst(WebSocketSession session){
        return Long.valueOf(getQueryParam(Objects.requireNonNull(session.getUri()).normalize(), Ai_Friend_Id));
    }

    public static String getQueryParam(URI uri, String paramName) {
        if (uri == null) return null;
        UriComponents components = UriComponentsBuilder.fromUri(uri).build();
        return components.getQueryParams().getFirst(paramName);
    }

}
