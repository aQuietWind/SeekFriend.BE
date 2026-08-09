package com.seek.friend.util.JWT;


import com.seek.friend.configobject.CommonData.JWTGlobalData;
import com.seek.friend.configobject.CommonData.JWTRoleData;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import com.seek.friend.util.TimeUtil.TimeUtil;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

//不能被Gateway引入，否则所持有的特殊类会导致冲突报错
public class TokenUtil {
    private static DefaultRedisScript<Boolean> tokenAddScript;

    private final RedisUtil redisUtil;
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public TokenUtil(RedisUtil redisUtil, StringRedisTemplate stringRedisTemplate) {
        this.redisUtil = redisUtil;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @PostConstruct
    public void init(){
        tokenAddScript=redisUtil.luaQuickInit("lua/token_add.lua");
    }

    //统一获取token
    public String getToken(Long id, HttpServletResponse response, JWTRoleData jwtRoleData, JWTGlobalData  jwtGlobalData) {
        //登录校验成功，生成JWT Token
        String accessToken = JWTUtil.obtainJwt(id, jwtRoleData.getSecretKey(),jwtRoleData.getTokenDuration());
        // 构建Servlet Cookie
        Cookie cookie = new Cookie(jwtGlobalData.getRequestHeaderTokenName(), jwtRoleData.getHeaderSign()+jwtGlobalData.getTokenHeaderSeparator()+accessToken);
        cookie.setHttpOnly(true);
        //生产环境改为true
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtRoleData.getTokenDuration()/1000));
        response.addCookie(cookie);
        return accessToken;
    }

    //统一进行token的获取与Redis存储
    //发放登录信息
    public void getAndRecordToken(Long tokenId, HttpServletResponse response, JWTRoleData jwtRoleData
            , JWTGlobalData  jwtGlobalData, RedisKeyData key){
        if (tokenId==null) throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //获取token，并且放在请求头上
        String token=getToken(tokenId, response,jwtRoleData,jwtGlobalData);
        stringRedisTemplate.execute(tokenAddScript         //执行脚本
                , redisUtil.toCollect(key.getRedisKey(tokenId))       //KEYS参数
                ,jwtGlobalData.getMaxStore(),token, ""+ TimeUtil.getStampByNow());     //ARGV参数
    }

    //检查token
    public boolean checkTokenIsExist(RedisKeyData key,Object id,String value){
        return redisUtil.zSetXIsExistByScore(key,id,value);
    }

}
