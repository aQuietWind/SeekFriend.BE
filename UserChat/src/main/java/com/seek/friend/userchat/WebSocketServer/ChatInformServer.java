package com.seek.friend.userchat.WebSocketServer;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.Common.JWTConfig;
import com.seek.friend.config.NacosConfig.UserChat.UserChatRedisKeyConfig;
import com.seek.friend.userchat.Mapper.UserChatRoomMapper;
import com.seek.friend.util.Context.TokenIdContext;
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

    private static final String Room_Id = "roomId";
    private static final String User_Id = "userId";

    // 存放所有在线会话
    private static final ConcurrentHashMap<Long, ConcurrentHashMap<Long,WebSocketSession>> Session_Map = new ConcurrentHashMap<>();
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final UserChatRedisKeyConfig userChatRedisKeyConfig;
    private final RedisUtil redisUtil;
    private final UserChatRoomMapper userChatRoomMapper;
    private final JWTConfig jwtConfig;

    @Autowired
    public ChatInformServer(CommonParamRulesConfig commonParamRulesConfig
    , UserChatRedisKeyConfig userChatRedisKeyConfig, RedisUtil redisUtil, UserChatRoomMapper userChatRoomMapper, JWTConfig jwtConfig) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.userChatRedisKeyConfig = userChatRedisKeyConfig;
        this.redisUtil = redisUtil;
        this.userChatRoomMapper = userChatRoomMapper;
        this.jwtConfig = jwtConfig;
    }

    // 连接建立成功
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception{
        Long roomId=quickGetRoomIdFirst(session);
        //检查该请求参数
        commonParamRulesConfig.commonIdCheck(roomId);
        //获取该请求账户的Id
        long userId=quickGetIdAndCheckCooldown(session);
        //检查该账户是否有权限监听该合格聊天室
        if (!userChatRoomMapper.checkRoomConnectionWithUser(roomId, userId))throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        //放置该session
        quickSaveSession(session,roomId,userId);
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
    public void broadcastRoomId(long roomId,String msg) throws IOException {
        ConcurrentHashMap<Long,WebSocketSession> map=Session_Map.get(roomId);
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
        redisUtil.checkCooldown(userChatRedisKeyConfig.getRoomInformListenCooldown(),userId);
        return userId;
    }

    private void quickRemoveSession(WebSocketSession session){
        Object roomId= session.getAttributes().get(Room_Id);
        if (roomId!=null)Session_Map.get((Long) roomId).remove( (Long) session.getAttributes().get(User_Id),session);
    }

    private void quickSaveSession(WebSocketSession session,long roomId,long userId) throws IOException {
        //先放置，方便后续拿取
        session.getAttributes().put(Room_Id, roomId);
        session.getAttributes().put(User_Id, userId);
        Session_Map.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        if (Session_Map.get(roomId).putIfAbsent(userId,session)!=null)session.close();
    }



    private Long quickGetRoomIdFirst(WebSocketSession session){
        return Long.valueOf(getQueryParam(Objects.requireNonNull(session.getUri()).normalize(),Room_Id));
    }

    public static String getQueryParam(URI uri, String paramName) {
        if (uri == null) return null;
        UriComponents components = UriComponentsBuilder.fromUri(uri).build();
        return components.getQueryParams().getFirst(paramName);
    }

}
